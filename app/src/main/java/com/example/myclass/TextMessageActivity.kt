package com.example.myclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TextMessageActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var textField: TextView
    private lateinit var textMessageRecycler: TextMessageRecycler
    private lateinit var recyclerView: RecyclerView

    private var messagelist = ArrayList<Message>()
    private var user = FirebaseAuth.getInstance().currentUser

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_message)

        val class_id = intent.getStringExtra("class_id")
        val layoutManager = LinearLayoutManager(applicationContext)
        val class_id = intent.getStringExtra("class_id")

        textMessageRecycler = TextMessageRecycler(messagelist)
        dbRef = FirebaseDatabase.getInstance().getReference("Message").child(class_id!!)

        recyclerView = findViewById(R.id.recycler_gchat)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = textMessageRecycler

        textField = findViewById(R.id.editMessage)

        val buttonsend =findViewById<Button>(R.id.buttonSend)
        buttonsend.setOnClickListener {
            val messageText = textField.text.toString()

            if (messageText.isNotEmpty()) {
                addMessage(messageText)
            } else {
                Toast.makeText(applicationContext, "Nous n'envoyons pas de message vide.", Toast.LENGTH_LONG).show()
            }
        }

        prepareItems()
    }

    private fun prepareItems(){
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    messagelist.clear()
                    for (e in dataSnapshot.children) {
                        val jazz = e.getValue(Message::class.java)
                        messagelist.add(jazz!!)
                    }
                } else {
                    Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
                }

                recyclerView.smoothScrollToPosition(messagelist.size)
                textMessageRecycler.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG)
            }
        })
    }

    private fun addMessage(text: String) {
        val messageKey = dbRef.push().key!!
        val newMessage = Message(messageKey, text, user!!.uid)

        dbRef.child(messageKey).setValue(newMessage)

        textField.text = ""
        textMessageRecycler.notifyDataSetChanged()
    }
}

