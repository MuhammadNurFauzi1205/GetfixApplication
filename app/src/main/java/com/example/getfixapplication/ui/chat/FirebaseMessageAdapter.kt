package com.example.getfixapplication.ui.chat

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.Chat
import com.example.getfixapplication.databinding.ItemMassageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseMessageAdapter(
    options: FirebaseRecyclerOptions<Chat>,
    private val currentUserName: String?
) : FirebaseRecyclerAdapter<Chat, FirebaseMessageAdapter.MessageViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_massage, parent, false)
        val binding = ItemMassageBinding.bind(view)
        return MessageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Chat) {
        holder.bind(model)
    }

    inner class MessageViewHolder(private val binding: ItemMassageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chat) {
            binding.tvMessage.text = item.text
            setTextColor(item.name, binding.tvMessage)
            binding.tvMessenger.text = item.name
            Glide.with(itemView.context)
                .load(item.photoUrl)
                .circleCrop()
                .into(binding.ivMessenger)
            if (item.timestamp != null) {
                binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
            }

        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.roundedborder)
            } else {
                textView.setBackgroundResource(R.drawable.gradient_background)
            }
        }
    }
}