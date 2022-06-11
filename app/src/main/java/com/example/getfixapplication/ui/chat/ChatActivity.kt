package com.example.getfixapplication.ui.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.Chat
import com.example.getfixapplication.databinding.ActivityChatBinding
import com.example.getfixapplication.databinding.ActivityLoginBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var bindingg: ActivityLoginBinding
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: FirebaseMessageAdapter
    var fire = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

//        val user = fire.collection("users")
//        user.get()
//        user.whereEqualTo("username", bindingg.ed1.editText?.text.toString())
//        val firebaseUser = fire.collection("users")
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        db = Firebase.database
        val messagesRef = db.reference.child(MESSAGES_CHILD)

        binding.sendButton.setOnClickListener {
            val friendlyMessage = Chat(
                binding.messageEditText.text.toString(),
                firebaseUser.displayName.toString(),
                firebaseUser.photoUrl.toString(),
                Date().time
            )
            messagesRef.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(
                        this,
                        getString(R.string.send_error) + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.messageEditText.setText("")
        }

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Chat>()
            .setQuery(messagesRef, Chat::class.java)
            .build()
        adapter = FirebaseMessageAdapter(options, firebaseUser.displayName)
        binding.messageRecyclerView.adapter = adapter


    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}