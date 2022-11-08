package com.example.myclass

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle

class MainActivity : Activity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            println(firebaseAuth.currentUser!!.email)
            setContentView(R.layout.activity_main)
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}