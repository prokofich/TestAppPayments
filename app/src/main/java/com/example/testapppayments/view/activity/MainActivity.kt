package com.example.testapppayments.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testapppayments.R
import com.example.testapppayments.databinding.ActivityMainBinding
import com.example.testapppayments.model.constant.MAIN

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        MAIN = this
        navController = Navigation.findNavController(this, R.id.id_nav_host)

    }

}