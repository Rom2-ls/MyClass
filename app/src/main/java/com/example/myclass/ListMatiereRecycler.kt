package com.example.myclass

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class ListMatiereRecycler(private var itemsList: List<Matiere>, private var classid: String) :
    RecyclerView.Adapter<ListMatiereRecycler.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var matiereTitle: TextView = view.findViewById(R.id.matiereTitle)
        //var matiereDoc: TextView = view.findViewById(R.id.matiereDoc)
        //var matiereImage: TextView = view.findViewById(R.id.matiereImage)
        init {
            view.setOnClickListener {
                val clickedItemId = adapterPosition
                val intent = Intent(itemView.context, DetailMatiereActivity::class.java)
                intent.putExtra("matiere_id", itemsList[clickedItemId].id)
                intent.putExtra("class_id", classid)
                itemView.context.startActivity(intent)
                Log.d("TEST", "item numero : " + itemsList[clickedItemId] )
                Log.d("TEST", "item numero : " + classid )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matiere, parent, false)

        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.matiereTitle.text = item.name
        //holder.matiereDoc.text = item.doc
        //holder.matiereImage.text = item.image
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}