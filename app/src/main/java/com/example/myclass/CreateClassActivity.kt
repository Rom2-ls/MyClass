package com.example.myclass

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myclass.databinding.ActivityCreateClassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateClassBinding
    private lateinit var database: DatabaseReference

    var user = FirebaseAuth.getInstance().currentUser

    data class newStudent(val role: String = "ADMIN")

    fun createClass(name: String) {
        database = Firebase.database.reference
        val newStudent = newStudent()

        database.child("Classes").push().child("Students").child(user!!.uid).setValue(newStudent)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference

        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btncreateclass.setOnClickListener{
            val name = binding.nameField.text.toString()

            Toast.makeText(applicationContext, name, Toast.LENGTH_LONG).show()
            createClass(name)
        }
    }
}