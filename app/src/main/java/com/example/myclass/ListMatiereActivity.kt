package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListMatiereActivity : AppCompatActivity() {
    lateinit var class_title: TextView
    private val itemsList = ArrayList<Matiere>()
    private lateinit var listMatiereRecycler: ListMatiereRecycler
    private lateinit var navView: BottomNavigationView

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
        val item = object : Matiere {
            override val title: String = "Developpement web"
            override val doc: String = "Doc 1"
            override val image: String = "Images 7"
        }

        val item2 = object : Matiere {
            override val title: String = "Anglais"
            override val doc: String = "Doc 1"
            override val image: String = "Images 7"
        }

        val item3 = object : Matiere {
            override val title: String = "Mobile"
            override val doc: String = "Doc 1"
            override val image: String = "Images 7"
        }


        itemsList.add(item)
        itemsList.add(item2)
        itemsList.add(item3)
        listMatiereRecycler.notifyDataSetChanged()
    }
}