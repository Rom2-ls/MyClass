package com.example.myclass

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

internal class ListClassRecycler(private var itemsList: List<Course>) :
    RecyclerView.Adapter<ListClassRecycler.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var classTitle: TextView = view.findViewById(R.id.classTitle)
        var classNumber: TextView = view.findViewById(R.id.classNumber)
        init {
            view.setOnClickListener {
                val clickedItemId = adapterPosition
                val intent = Intent(itemView.context, ListMatiereActivity::class.java)
                intent.putExtra("class_id", itemsList[clickedItemId].id)
                itemView.context.startActivity(intent)
                Log.d("TEST", "item numero : " + itemsList[clickedItemId].name )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.classTitle.text = item.name
        holder.classNumber.text = item.nb_students.toString()
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}