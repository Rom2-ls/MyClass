package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListMessageActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Course>()
    private lateinit var listClassRecycler: ListClassRecycler
    private lateinit var dbRef: DatabaseReference
    private lateinit var navView: BottomNavigationView

    var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_message)
        navView = findViewById(R.id.nav_view)
        listClassRecycler = ListClassRecycler(itemsList)
        navView.selectedItemId = R.id.navigation_home
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMessage)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listClassRecycler

        prepareItems()

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
                    val intent = Intent(this, ListMessageActivity::class.java)
                    startActivity(intent)
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

    private fun prepareItems() {

        dbRef = FirebaseDatabase.getInstance().getReference("Classes")
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemsList.clear()
                    for (e in dataSnapshot.children) {
                        if (checkIfContainId(e.child("Students"))) {
                            val jack = e.getValue(Course::class.java)
                            itemsList.add(jack!!)
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
                }
                listClassRecycler.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
            }
        })
    }

    private fun checkIfContainId(dataSnapshot: DataSnapshot): Boolean {
        if (dataSnapshot.hasChild(user!!.uid)) { return true }
        return false
    }
}