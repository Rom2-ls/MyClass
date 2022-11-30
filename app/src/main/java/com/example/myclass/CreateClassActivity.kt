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

    var currentUser = FirebaseAuth.getInstance().currentUser


    data class NewClass(val name: String = "")
    data class NewStudent(val role: String = "ADMIN")

    private fun createClass(name: String) {
        database = Firebase.database.reference

        val newclass = NewClass(name);
        val newstudent = NewStudent()
        val classId = database.child("Classes").push().key
        database.child("Classes").child(classId.toString()).setValue(newclass)
        database.child("Classes").child(classId.toString()).child("Students").child(currentUser!!.uid).setValue(newstudent)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btncreateclass.setOnClickListener {
            val name = binding.nameField.text.toString()

            if (name.isNotEmpty()) {
                createClass(name)
                Toast.makeText(applicationContext,"toast after database",Toast.LENGTH_LONG).show()
                // rediriger vers la page de liste des classes
            }
        }
    }
}