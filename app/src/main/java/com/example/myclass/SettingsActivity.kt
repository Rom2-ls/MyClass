package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    lateinit var hello_text: TextView
    lateinit var btnlgt: AppCompatButton
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email

        setContentView(R.layout.activity_settings)
        navView = findViewById(R.id.nav_view)
        hello_text = findViewById(R.id.hellotext)
        navView.selectedItemId = R.id.navigation_parametres
        hello_text.setText("Bonjour " + email)
        btnlgt = findViewById(R.id.button)
        btnlgt.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("TEST", "switch to acticity home")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_message -> {
                    Log.d("TEST", "switch to acticity dash")
                    true
                }
                R.id.navigation_parametres -> {
                    Log.d("TEST", "switch to acticity param")
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}