package com.thirumaran.superhero.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thirumaran.superhero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

    }
}