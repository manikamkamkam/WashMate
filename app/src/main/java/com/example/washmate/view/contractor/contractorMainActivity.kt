package com.example.washmate.view.contractor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.washmate.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class contractorMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contractor_activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.contractorbottomNavView)
        val navController =  findNavController(R.id.contratorViewFragment)
        AppBarConfiguration(setOf(R.id.contractor_home, R.id.contractor_schedule, R.id.contractor_schedule))
        navView.setupWithNavController(navController)

    }
}