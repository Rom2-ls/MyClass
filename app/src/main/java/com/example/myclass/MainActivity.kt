package com.example.myclass

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.myclass.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogout.setOnClickListener({
            firebaseAuth.signOut()
            finish()
            startActivity(getIntent())
        });

        if(firebaseAuth.currentUser != null){
            println(firebaseAuth.currentUser!!.email)
            setContentView(binding.root)
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}