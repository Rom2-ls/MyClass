package com.example.myclass


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.Objects

interface Classe {
    val title: String
    val number: String
}

internal class ListClassRecycler(private var itemsList: List<Classe>) :
    RecyclerView.Adapter<ListClassRecycler.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var classTitle: TextView = view.findViewById(R.id.classTitle)
        var classNumber: TextView = view.findViewById(R.id.classNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.classTitle.text = item.title
        holder.classNumber.text = item.number
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}