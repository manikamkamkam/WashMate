package com.example.washmate.view.customer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.washmate.R
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.washmate.stripe.FirebaseEphemeralKeyProvider
import com.example.washmate.view.customDialog.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.stripe.android.*
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.BillingAddressFields
import kotlinx.android.synthetic.main.customer_carwash_activity.*
import java.text.DateFormat
import java.util.*
import android.widget.TextView

import android.widget.TimePicker
import com.example.washmate.model.car
import com.example.washmate.model.role.User
import com.example.washmate.model.role.customer
import com.example.washmate.view.customDialog.paymentStatusDialog
import kotlin.collections.ArrayList


public class CarwashActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    private val loggedInUser : customer = customer.getLoggedinUser()
    private var context: Context? = null
    private lateinit var paymentSession: PaymentSession
    private lateinit var selectedPaymentMethod: PaymentMethod
    private val stripe: Stripe by lazy { Stripe(applicationContext, "pk_test_51JvRPmBsSDcrnJguXgZxcZnCVs4j5SCsAQX2oaIYfuOI3rFxL9U66KrggDo2JW9I1IZPbSmm1CTplBSFaLY7JTmH00XCTndmZL")}
    var cars =  car().getCurrentUserCars(car.carCallBack {
        setCarplateNo()
    })

    val ld = LoadingDialog(this)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_carwash_activity)
        context = this
        val dropdown = findViewById<Spinner>(R.id.carsSpinner)
        val cars : ArrayList<car> = car().getCurrentUserCars(car.carCallBack {
            setCarplateNo()
            })

        val date = findViewById<EditText>(R.id.appointment_date)
        val time = findViewById<EditText>(R.id.appointment_time)
        val backButton = findViewById<ImageView>(R.id.backArrow)

        backButton.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CustomerMainActivity::class.java
                )
            )
        }
        date.setOnClickListener {
            val datePicker: DialogFragment = datePickerFragment()
            datePicker.show(supportFragmentManager, "date")

        }
        time.setOnClickListener {
            val timePicker: DialogFragment = timePickerFragment()
            timePicker.show(supportFragmentManager, "time")
        }

        paymentMethodBtn.setOnClickListener {
            // Create the customer session and kick start the payment flow
            paymentSession.presentPaymentMethodSelection()

        }
        bookBtn.isEnabled = false
        bookBtn.setOnClickListener{
            confirmPayment(selectedPaymentMethod.id!!)

            ld.startLoadingDialog()
        }

        setupPaymentSession()
    }

    private fun setCarplateNo()
    {
        val carplateNo: ArrayList<String> = ArrayList()
        for (plateNum in cars) {
            carplateNo.add(plateNum.carplateNum)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,carplateNo)
        carsSpinner.adapter = adapter
    }

    private fun confirmPayment(paymentMethodId: String) {
        bookBtn.isEnabled = false

        val paymentCollection = Firebase.firestore
            .collection("stripe_customers").document(FirebaseAuth.getInstance()?.uid?:"")
            .collection("payments")

        // Add a new document with a generated ID
        paymentCollection.add(hashMapOf(
            "amount" to (TotalAmount.text.toString().toFloat()*100),
            "currency" to "MYR"
        ))
            .addOnSuccessListener { documentReference ->
                Log.d("payment", "DocumentSnapshot added with ID: ${documentReference.id}")
                documentReference.addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("payment", "Listen failed.", e)
                        showPaymentFailedDialog()
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("payment", "Current data: ${snapshot.data}")
                        val clientSecret = snapshot.data?.get("client_secret")
                        Log.d("payment", "Create paymentIntent returns $clientSecret")
                        clientSecret?.let {
                            stripe.confirmPayment(this, ConfirmPaymentIntentParams.createWithPaymentMethodId(
                                paymentMethodId,
                                (it as String)
                            ))

                            ld.dismissDialog()
                            showPaymentSuccessDialog()
                            Toast.makeText(applicationContext, "Payment Done!!", Toast.LENGTH_LONG).show()
                            val car = cars.find{s->s.carplateNum.equals(carsSpinner.selectedItem.toString())}

                            if (car != null) {
                                loggedInUser.makeAppointment(appointment_date.text.toString(),appointment_time.text.toString(),car.carType,car.carplateNum,address.text.toString(),TotalAmount.text.toString(),loggedInUser.uId)
                            }

                        }
                    } else {
                        Log.e("payment", "Current payment intent : null")
                        bookBtn.isEnabled = true
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("payment", "Error adding document", e)
                bookBtn.isEnabled = true
            }
    }

    private fun setupPaymentSession ()
    {
        CustomerSession.initCustomerSession(this,FirebaseEphemeralKeyProvider())

        paymentSession = PaymentSession(this,PaymentSessionConfig.Builder()
            .setShippingInfoRequired(false)
            .setShippingMethodsRequired(false)
            .setBillingAddressFields(BillingAddressFields.None)
            .setShouldShowGooglePay(true)
            .build()

        )

        paymentSession.init(
            object: PaymentSession.PaymentSessionListener {
                override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                    Log.d("PaymentSession", "PaymentSession has changed: $data")
                    Log.d("PaymentSession", "${data.isPaymentReadyToCharge} <> ${data.paymentMethod}")

                    if (data.isPaymentReadyToCharge) {
                        Log.d("PaymentSession", "Ready to charge");
                        bookBtn.isEnabled = true

                        data.paymentMethod?.let {
                            Log.d("PaymentSession", "PaymentMethod $it selected")
                            paymentMethodBtn.text =
                                "${it.card?.brand} card ends with ${it.card?.last4}"
                            selectedPaymentMethod = it
                        }
                    }

                }


                override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                    Log.d("PaymentSession",  "isCommunicating $isCommunicating")
                }

                override fun onError(errorCode: Int, errorMessage: String) {
                    Log.e("PaymentSession",  "onError: $errorCode, $errorMessage")
                }
            }
        )

    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        val RC_SIGN_IN =1
        if (requestCode == RC_SIGN_IN) {

        } else {
            paymentSession.handlePaymentData(requestCode, resultCode, data ?: Intent())
        }

}
    fun showPaymentSuccessDialog()
    {

       val psDialog: paymentStatusDialog  =  paymentStatusDialog(this,object : DialogInterface.OnClickListener {
           override fun onClick(dialog: DialogInterface?, which: Int) {
           finish()
           }
       })
       psDialog.startSuccessDialog()

    }

    fun showPaymentFailedDialog()
    {
        val psDialog: paymentStatusDialog  =  paymentStatusDialog(this,object :DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
            })

        psDialog.startfailedDialog()
    }











    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val dateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        val date = findViewById<EditText>(R.id.appointment_date)
        date.setText(dateSelected)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val textView = findViewById<View>(R.id.appointment_time) as TextView
        textView.text = "$hourOfDay : $minute"
    }


}