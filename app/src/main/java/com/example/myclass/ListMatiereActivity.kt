package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListMatiereActivity : AppCompatActivity() {
    lateinit var class_title: TextView
    private val itemsList = ArrayList<Matiere>()
    private lateinit var listMatiereRecycler: ListMatiereRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_matiere)

        val class_id = intent.getStringExtra("class")
        val class_title = findViewById<TextView>(R.id.classTitle)
        class_title.setText(class_id)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMatiere)
        listMatiereRecycler = ListMatiereRecycler(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listMatiereRecycler
        prepareItems()
    }
    private fun prepareItems() {
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