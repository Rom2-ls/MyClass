package com.example.myclass

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DetailMatiereRecycler(private val imageList: ArrayList<ImageCours>, private val context: Context) :
    RecyclerView.Adapter<DetailMatiereRecycler.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = view.findViewById(R.id.imageDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_show, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // loading the images from the position
        Log.d("url", imageList[position].url.toString())
        Glide.with(context).load(imageList[position].url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}