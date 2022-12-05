package com.example.myclass

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myclass.databinding.ActivityJoinClassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinClassBinding
    private lateinit var database: DatabaseReference
    private lateinit var dbRef: DatabaseReference

    var user = FirebaseAuth.getInstance().currentUser

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnjoinclass.setOnClickListener{
            val classLink = binding.classLink.toString()

            if (classLink.isNotEmpty()) {
                joinClass(classLink)
            } else {
                Toast.makeText(applicationContext, "Donnez un lien de classe", Toast.LENGTH_LONG)
            }
        }
    }

    private fun joinClass(classLink: String) {
        //check if class exists
        dbRef = FirebaseDatabase.getInstance().getReference("Classes").child(classLink)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("join class datasnapshot", dataSnapshot.toString())
                if (dataSnapshot.exists()) {
                    addStudentToClass(classLink)
                } else {
                    Toast.makeText(applicationContext, "Cette classe n'existe pas", Toast.LENGTH_LONG)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
            }
        })
    }

    private fun addStudentToClass(classLink: String) {
        database = Firebase.database.reference

        val newStudent = Student(user!!.uid, "USER")

        //méthode 1
        database.child("Classes").child(classLink).child("Students").child(user!!.uid).setValue(newStudent)

        //méthode 2
    }
}