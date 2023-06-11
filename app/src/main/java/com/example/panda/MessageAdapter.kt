package com.example.panda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.panda.R

class MessageAdapter(diffCallback: DiffUtil.ItemCallback<Message>) :
    ListAdapter<Message, MessageAdapter.MessageViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)

        fun bind(message: Message) {
            messageTextView.text = message.text
            if (message.isUserMessage) {
                messageTextView.setBackgroundResource(R.drawable.user_message_background)
            } else {
                messageTextView.setBackgroundResource(R.drawable.bot_message_background)
            }
        }
    }
}


