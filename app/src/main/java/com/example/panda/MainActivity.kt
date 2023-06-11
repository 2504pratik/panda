package com.example.panda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startChatButton: Button
    private lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startChatButton = findViewById(R.id.startChatButton)
        usernameEditText = findViewById(R.id.usernameEditText)

        startChatButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            if (username.isNotEmpty()) {
                val intent = Intent(this@MainActivity, ChatActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                usernameEditText.error = "Please enter a username"
            }
        }
    }
}

