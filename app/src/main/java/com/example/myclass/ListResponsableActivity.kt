package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListResponsableActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Responsable>()
    private lateinit var listResponsableRecycler: ListResponsableRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_responsable)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewResponsable)
        listResponsableRecycler = ListResponsableRecycler(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listResponsableRecycler
        prepareItems()
    }

    private fun prepareItems() {
        val item = object : Responsable {
            override val name: String = "John Doe"
            override val date: String = "10/01/2022"
        }

        val item2 = object : Responsable {
            override val name: String = "John2 Doe2"
            override val date: String = "10/01/2022"
        }

        val item3 = object : Responsable {
            override val name: String = "John3 Doe3"
            override val date: String = "10/01/2022"
        }


        itemsList.add(item)
        itemsList.add(item2)
        itemsList.add(item3)
        listResponsableRecycler.notifyDataSetChanged()
    }
}