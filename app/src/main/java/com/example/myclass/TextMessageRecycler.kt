package com.example.myclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

internal class TextMessageRecycler(private var messageList: ArrayList<Message>) :
    RecyclerView.Adapter<TextMessageRecycler.MyViewHolder>() {

    private val VIEW_TYPE_ITEM_1 = 1
    private val VIEW_TYPE_ITEM_2 = 2

    val user = FirebaseAuth.getInstance().currentUser
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //var messageStudent: TextView = view.findViewById(R.id.text_gchat_timestamp_me)

        var message: TextView = view.findViewById(R.id.text_gchat_message_me)
        //var message1: TextView = view.findViewById(R.id.text_gchat_message_other)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        /*if (viewType == 1) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_me, parent, false)

            return MyViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_you, parent, false)

            return MyViewHolder(itemView)
        }*/
        return when (viewType) {
            VIEW_TYPE_ITEM_1 -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_me, parent, false))
            VIEW_TYPE_ITEM_2 -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_you, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }



    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = messageList[position]
        //holder.messageStudent.text = item.idStudent
        holder.message.text = item.message
        //holder.message1.text = item.message
        //holder.messageId.text = item.id
    }

    override fun getItemViewType(position: Int): Int {
        val item = messageList[position]
        //if (item.idStudent == user!!.uid) {

        if (item.idStudent == "1"){
            return VIEW_TYPE_ITEM_1
        } else {
            return VIEW_TYPE_ITEM_2
        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
}