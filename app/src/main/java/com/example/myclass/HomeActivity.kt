package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.example.myclass.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = findViewById(R.id.nav_view)

        binding.btnJoinClassActivity.setOnClickListener {
            val intent = Intent(this, JoinClassActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateClassActivity.setOnClickListener {
            val intent = Intent(this, CreateClassActivity::class.java)
            startActivity(intent)
        }

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("TEST", "switch to acticity home")
                    true
                }
                R.id.navigation_message -> {
                    Log.d("TEST", "switch to acticity dash")
                    true
                }
                R.id.navigation_parametres -> {
                    Log.d("TEST", "switch to acticity param")
                    true
                }
                else -> {
                    true
                }
            }
            }
    }
}