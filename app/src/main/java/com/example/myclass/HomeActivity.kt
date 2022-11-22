package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.example.myclass.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJoinClassActivity.setOnClickListener {
            val intent = Intent(this, JoinClassActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateClassActivity.setOnClickListener {
            val intent = Intent(this, CreateClassActivity::class.java)
            startActivity(intent)
        }
    }
}