package com.example.myclass

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.example.myclass.databinding.ActivityCreateClassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateClassBinding
    private lateinit var database: DatabaseReference

    private val REQUEST_READ_EXTERNAL_STORAGE = 333

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
        database.child("Classes").child(classKey).child("Message")

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //Pas encore la permission donc on la demande ici :
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("classID", classKey)
            clipboard.setPrimaryClip(clip)
        }
        Toast.makeText(this, "L'ID est copié dans le presse papier!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ListClassActivity::class.java)
        startActivity(intent)
    }
}