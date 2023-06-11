package com.example.panda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Retrieve the username from the intent
        username = intent.getStringExtra("username") ?: "Guest"

        // Set the welcome message
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Welcome, $username!"

        // Create the adapter for the chat messages RecyclerView
        messageAdapter = MessageAdapter(MessageDiffCallback())

        // Set up the RecyclerView
        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }

        // Send button click listener
        val sendButton = findViewById<Button>(R.id.sendButton)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            sendMessage(message)
            messageEditText.text.clear()
        }

        // Send initial bot message
        val initialBotMessage = Message("Hey, I am a Panda. I will be your assistant. I am still in development phase.", false)
        sendMessage(initialBotMessage.text)
    }

    private fun sendMessage(message: String) {
        if (message.isNotEmpty()) {
            val userMessage = Message(message, true)
            val botMessage = Message("This is a bot response.", false)

            val messages = messageAdapter.currentList.toMutableList()

            // Add user message
            messages.add(userMessage)

            // Add initial bot message
            if (messages.isEmpty()) {
                val initialBotMessage = Message("Hey, I am a Panda. I will be your assistant. I am still in development phase.", false)
                messages.add(initialBotMessage)
            }

            // Add bot response
            messages.add(botMessage)

            messageAdapter.submitList(messages)

            // Scroll to the latest message
            messageRecyclerView.scrollToPosition(messages.size - 1)
        }
    }


    private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.text == newItem.text && oldItem.isUserMessage == newItem.isUserMessage
        }
    }

}


