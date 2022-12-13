package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            val collection = FirebaseDatabase.getInstance().getReference("Students")
            collection.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(firebaseAuth.currentUser!!.uid).exists()) {
                        redirectCustom(true)
                    } else {
                        redirectCustom(false)
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    fun redirectCustom(hasClass: Boolean) {
        if (hasClass) {
            val intent = Intent(this, ListClassActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}