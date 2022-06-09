package com.example.getfixapplication.ui.chat

import android.os.Message
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getfixapplication.databinding.ItemMassageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseMessageAdapter(
    options: FirebaseRecyclerOptions<Message>,
    private val currentUserName: String?
) : FirebaseRecyclerAdapter<Message, FirebaseMessageAdapter.MessageViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
    }
    inner class MessageViewHolder(private val binding: ItemMassageBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}