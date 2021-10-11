package com.example.washmate.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.washmate.R
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavview = findViewById<BottomNavigationView>(R.id.main_nav)
        val navcontroller = findNavController(this,R.id.framelayout)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home,R.id.appointment,R.id.profile))
        setupActionBarWithNavController(navcontroller,appBarConfiguration)
        bottomNavview.setupWithNavController(navcontroller)
    }
}