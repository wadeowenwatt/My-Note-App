package com.example.mynotes.ui.main

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.example.mynotes.R
import com.example.mynotes.data.DBHelper
import com.example.mynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}