package com.example.myclass

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclass.databinding.ActivityJoinClassBinding
import com.example.myclass.databinding.ActivityListMatiereBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_matiere.*

class ListMatiereActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Matiere>()
    private lateinit var listMatiereRecycler: ListMatiereRecycler
    private lateinit var add_button: TextView
    private lateinit var add_content: TextInputEditText
    private lateinit var buttonCopy: TextView
    private lateinit var dbRef: DatabaseReference

    private val REQUEST_READ_EXTERNAL_STORAGE = 333
    private lateinit var binding: ActivityListMatiereBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_matiere)
        add_button = findViewById(R.id.add_button)
        add_content = findViewById(R.id.add_content)
        buttonCopy = findViewById(R.id.buttonCopy)

        val class_id = intent.getStringExtra("class_id")
        val class_title = findViewById<TextView>(R.id.classTitle)
        class_title.text = class_id

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMatiere)
        listMatiereRecycler = ListMatiereRecycler(itemsList, class_id!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listMatiereRecycler

        prepareItems(class_id)

        add_button.setOnClickListener {
            val name = add_content.text.toString()

            if (name.isNotEmpty()) {
                addMatiere(class_id, name)
            } else {
                Toast.makeText(applicationContext, "Donnez un nom à votre matiere", Toast.LENGTH_LONG).show()
            }
        }

        //binding = ActivityListMatiereBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        buttonCopy.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //Pas encore la permission donc on la demande ici :
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
            }else{
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("classID", class_id)
                clipboard.setPrimaryClip(clip)
            }
            Toast.makeText(this, "L'ID est copié dans le presse papier!", Toast.LENGTH_LONG).show()
        }
    }

    private fun addMatiere(class_id: String?, name: String?) {
        val matiereKey = dbRef.push().key!!
        val newMatiere = Matiere(matiereKey, name!!)

        dbRef.child(matiereKey).setValue(newMatiere)

        add_content.setText("")
        listMatiereRecycler.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Matière bien ajoutée", Toast.LENGTH_LONG).show()
    }

    private fun prepareItems(class_id: String?) {
        dbRef = FirebaseDatabase.getInstance().getReference("Classes").child(class_id!!).child("Matiere")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemsList.clear()
                    for (e in dataSnapshot.children) {
                        val uzi = e.getValue(Matiere::class.java)
                        itemsList.add(uzi!!)
                    }
                } else {
                    Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
                }
                listMatiereRecycler.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
            }
        })

        listMatiereRecycler.notifyDataSetChanged()
    }
}