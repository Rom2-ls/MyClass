package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclass.databinding.ActivityCreateClassBinding
import com.example.myclass.databinding.ActivityTextMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TextMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextMessageBinding
    private lateinit var dbRef: DatabaseReference
    private val messagelist = ArrayList<Message>()

    private lateinit var textMessageRecycler: TextMessageRecycler

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_message)

      /*  binding = ActivityTextMessageBinding.inflate(layoutInflater)
        setContentView(binding.root) */

        textMessageRecycler = TextMessageRecycler(messagelist)

        val buttonsend =findViewById<Button>(R.id.buttonSend)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_gchat)
        val layoutManager = LinearLayoutManager(applicationContext)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = textMessageRecycler
        prepareItems()
    }
    private fun prepareItems(){
        val message1 = Message("id1", "salut", "1")
        val message2 = Message("id2", "salut1", "0")
        val message3 = Message("id3", "salut2", "0")
        messagelist.add(message1)
        messagelist.add(message2)
        messagelist.add(message3)

        textMessageRecycler.notifyDataSetChanged()


    }
}

