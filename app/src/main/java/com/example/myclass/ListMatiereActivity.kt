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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListMatiereActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Matiere>()
    private lateinit var listMatiereRecycler: ListMatiereRecycler
    private lateinit var navView: BottomNavigationView
    private lateinit var dbRef: DatabaseReference

    var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_matiere)
        navView = findViewById(R.id.nav_view)

        val class_id = intent.getStringExtra("class_id")
        val class_title = findViewById<TextView>(R.id.classTitle)
        class_title.setText(class_id)
        navView.selectedItemId = R.id.navigation_home

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMatiere)
        listMatiereRecycler = ListMatiereRecycler(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listMatiereRecycler
        prepareItems(class_id)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("TEST", "switch to acticity home")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_message -> {
                    Log.d("TEST", "switch to acticity dash")
                    true
                }
                R.id.navigation_parametres -> {
                    Log.d("TEST", "switch to acticity param")
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun prepareItems(class_id: String?) {
        val item = Matiere("id1", "Mathématiques")
        val item2 = Matiere("id2", "Histoire")
        val item3 = Matiere("id3", "Anglais")

        itemsList.add(item)
        itemsList.add(item2)
        itemsList.add(item3)

        dbRef = FirebaseDatabase.getInstance().getReference("Classes").child("Matiere")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemsList.clear()
                    for (e in dataSnapshot.children) {
                        val matiere = e.getValue(Matiere::class.java)
                        itemsList.add(matiere!!)
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