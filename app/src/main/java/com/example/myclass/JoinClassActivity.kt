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
            val classLink = binding.classLink.text.toString()

            if (classLink.isNotEmpty()) {
                joinClass(classLink)
            } else {
                Toast.makeText(applicationContext, "Donnez un lien de classe", Toast.LENGTH_LONG)
            }
        }
    }

    private fun joinClass(classLink: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Classes")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(classLink) && checkIfNotInClass(dataSnapshot.child("Students"))) {
                    addStudentToClass(classLink)
                } else {
                    Toast.makeText(applicationContext, "Cette classe n'existe pas ou Vous appartenez déjà à cette classe", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addStudentToClass(classLink: String) {
        database = Firebase.database.reference

        val newStudent = Student(user!!.uid, "USER")

        database.child("Students").child(user!!.uid).child("Classes").child(classLink).setValue(classLink)
        database.child("Classes").child(classLink).child("Students").child(user!!.uid).setValue(newStudent)
    }

    private fun checkIfNotInClass(dataSnapshot: DataSnapshot): Boolean {
        if (dataSnapshot.hasChild(user!!.uid)) { return false }
        return true
    }
}