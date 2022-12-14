package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListMatiereActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Matiere>()
    private lateinit var listMatiereRecycler: ListMatiereRecycler
    private lateinit var add_button: TextView
    private lateinit var add_content: TextInputEditText
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_matiere)
        add_button = findViewById(R.id.add_button)
        add_content = findViewById(R.id.add_content)

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