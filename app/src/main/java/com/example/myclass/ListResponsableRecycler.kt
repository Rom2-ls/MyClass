package com.example.myclass


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.Objects

interface Responsable {
    val name: String
    val date: String
}

internal class ListResponsableRecycler(private var itemsList: List<Responsable>) :
    RecyclerView.Adapter<ListResponsableRecycler.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var responsableName: TextView = view.findViewById(R.id.responsableName)
        var responsableDate: TextView = view.findViewById(R.id.responsableDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_responsable, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.responsableName.text = item.name
        holder.responsableDate.text = item.date
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}