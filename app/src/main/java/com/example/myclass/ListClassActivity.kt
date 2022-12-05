package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListClassActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Course>()
    private lateinit var listClassRecycler: ListClassRecycler
    private lateinit var dbRef: DatabaseReference
    var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_class)

        listClassRecycler = ListClassRecycler(itemsList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewClass)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listClassRecycler
        prepareItems()
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