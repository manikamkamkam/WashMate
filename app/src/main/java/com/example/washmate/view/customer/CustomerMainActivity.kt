package com.example.washmate.view.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

import com.example.washmate.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stripe.android.PaymentConfiguration

class CustomerMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_activity_main)

        val navView = findViewById<BottomNavigationView>(R.id.customerBottomNavView)
        val navController =  findNavController(R.id.customerViewFragment)
        AppBarConfiguration(setOf(R.id.customer_home,R.id.customer_appointment,R.id.customer_profile))
        navView.setupWithNavController(navController)

    }
}