package com.example.myclass

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myclass.databinding.ActivityCreateClassBinding

class CreateClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateClassBinding

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}