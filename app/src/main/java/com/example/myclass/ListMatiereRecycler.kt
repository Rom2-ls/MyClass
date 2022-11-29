package com.example.myclass


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.Objects

interface Matiere {
    val title: String
    val doc: String
    val image: String
}

internal class ListMatiereRecycler(private var itemsList: List<Matiere>) :
    RecyclerView.Adapter<ListMatiereRecycler.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var matiereTitle: TextView = view.findViewById(R.id.matiereTitle)
        var matiereDoc: TextView = view.findViewById(R.id.matiereDoc)
        var matiereImage: TextView = view.findViewById(R.id.matiereImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matiere, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.matiereTitle.text = item.title
        holder.matiereDoc.text = item.doc
        holder.matiereImage.text = item.image
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}