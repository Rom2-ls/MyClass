package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListClassActivity : AppCompatActivity() {
    private val itemsList = ArrayList<Classe>()
    private lateinit var listClassRecycler: ListClassRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_class)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewClass)
        listClassRecycler = ListClassRecycler(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listClassRecycler
        prepareItems()
    }

    private fun prepareItems() {
        val item = object : Classe {
            override val title: String = "Developpement web"
            override val number: String = "Nombre 15"
        }

        val item2 = object : Classe {
            override val title: String = "Cyber"
            override val number: String = "Nombre 25"
        }

        val item3 = object : Classe {
            override val title: String = "Data"
            override val number: String = "Nombre 30"
        }
        itemsList.add(item)
        itemsList.add(item2)
        itemsList.add(item3)
        listClassRecycler.notifyDataSetChanged()
    }
}