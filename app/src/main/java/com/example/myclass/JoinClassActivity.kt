package com.example.myclass

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myclass.databinding.ActivityJoinClassBinding

class JoinClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinClassBinding

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}