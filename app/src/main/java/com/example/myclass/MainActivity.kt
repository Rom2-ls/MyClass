package com.example.myclass

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myclass.ui.theme.MyClassTheme


class MainActivity : ComponentActivity() {
    lateinit var username: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val button = findViewById<View>(R.id.loginBtnId)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                println("TOAST")
                println(username.text.toString())
                println(password.text.toString())
//                println(username.va)
//                println(password.text.toString())
            }
        })
    }
}
