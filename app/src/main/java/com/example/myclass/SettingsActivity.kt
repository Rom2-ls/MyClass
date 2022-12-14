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
    lateinit var btnadd: AppCompatButton
    lateinit var btnjoin: AppCompatButton
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email

        setContentView(R.layout.activity_settings)
        navView = findViewById(R.id.nav_view)
        hello_text = findViewById(R.id.hellotext)
        navView.selectedItemId = R.id.navigation_parametres
        hello_text.setText("Compte : " + email)
        btnlgt = findViewById(R.id.button)
        btnadd = findViewById(R.id.button1)
        btnjoin = findViewById(R.id.button2)
        btnlgt.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        btnadd.setOnClickListener {
            val intent = Intent(this, CreateClassActivity::class.java)
            startActivity(intent)
        }
        btnjoin.setOnClickListener {
            val intent = Intent(this, JoinClassActivity::class.java)
            startActivity(intent)
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("TEST", "switch to acticity home")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_message -> {
                    Log.d("TEST", "switch to acticity dash")
                    val intent = Intent(this, ListMessageActivity::class.java)
                    startActivity(intent)
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