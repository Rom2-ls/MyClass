package com.example.myclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myclass.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogout.setOnClickListener({
            firebaseAuth.signOut()
            finish()
            startActivity(getIntent())
        });

        if(firebaseAuth.currentUser != null){
            println(firebaseAuth.currentUser!!.email)
            setContentView(binding.root)
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)

        button.setOnClickListener {
            val intent = Intent(this, ListClassActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, ListMatiereActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, ListResponsableActivity::class.java)
            startActivity(intent)
        }

        button4.setOnClickListener {
            val intent = Intent(this, DetailCoursActivity::class.java)
            startActivity(intent)
        }

        button5.setOnClickListener {
            val intent = Intent(this, DetailMatiereActivity::class.java)
            startActivity(intent)
        }

        button6.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

//        val intent = Intent(this, ListResponsableActivity::class.java)
//        startActivity(intent)
    }
}