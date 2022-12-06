package com.example.myclass

import android.content.Intent
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

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btncreateclass.setOnClickListener{
            val name = binding.nameField.text.toString()

            if (name.isNotEmpty()) {
                createClass(name)
            } else {
                Toast.makeText(applicationContext, "Donnez un nom à votre classe", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createClass(name: String) {
        database = Firebase.database.reference

        val newStudent = Student(id = user!!.uid, role = "ADMIN")
        val classKey = database.push().key!!
        val newClass = Course(classKey, name)

        database.child("Students").child(user!!.uid).child("Classes").child(newClass!!.id).setValue(newClass)
        database.child("Classes").child(classKey).setValue(newClass)
        database.child("Classes").child(classKey).child("Students").child(user!!.uid).setValue(newStudent)
        Toast.makeText(this, "La classe a bien été créée !", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ListClassActivity::class.java)
        startActivity(intent)
    }
}