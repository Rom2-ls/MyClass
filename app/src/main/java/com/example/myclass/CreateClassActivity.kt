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

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btncreateclass.setOnClickListener{
            val name = binding.nameField.text.toString()

            createClass(name)
        }
    }

    fun createClass(name: String) {
        if (name.isNotEmpty()) {
            database = Firebase.database.reference
            //ref Course.kt et ListClassActivity.kt

            //méthode 1 et 2
            val newStudent = Student(id = user!!.uid, role = "ADMIN")

            //méthode 1
            val newClass = Course(name)

            //méthode 2
            //val studentList = ArrayList<Student>()
            //studentList.add(newStudent)
            //val newClass = Course(name, students = studentList)

            //méthode 1 et 2
            val classKey = database.push().key!!

            //méthode 1 et 2
            database.child("Classes").child(classKey).setValue(newClass)

            //méthode 1
            database.child("Classes").child(classKey).child("Students").child(user!!.uid).setValue(newStudent)

            Toast.makeText(applicationContext, "Classe créée", Toast.LENGTH_LONG)
        } else {
            Toast.makeText(applicationContext, "Donnez un nom à votre classe", Toast.LENGTH_LONG)
        }
    }
}