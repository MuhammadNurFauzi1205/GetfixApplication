package com.example.getfixapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.google.firebase.database.*

class SignupActivity : AppCompatActivity() {
    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivitySignupBinding
    var USERNAME_KEY = "usernamekey"
    var username_key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val username = binding.ed1.text.toString()
        val email = binding.ed2.text.toString()
        val password = binding.ed3.text.toString()

        binding.btnSign.setOnClickListener {
            val sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(username_key, username)
            editor.apply()

            myreference = FirebaseDatabase.getInstance().reference
                .child("users").child(email)

            myreference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.ref.child("username").setValue(username)
                    snapshot.ref.child("email").setValue(email)
                    snapshot.ref.child("password").setValue(password)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
        }
    }
}