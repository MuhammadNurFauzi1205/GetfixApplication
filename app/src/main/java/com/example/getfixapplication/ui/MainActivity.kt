package com.example.getfixapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivityMainBinding
    var USERNAME_KEY = "usernamekey"
    var username_key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            val emaill = binding.ed1.text.toString()
            val password = binding.ed2.text.toString()

            myreference = FirebaseDatabase.getInstance().reference
                .child("users").child(emaill)

            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val passwordFromFirebase = snapshot.child("password").value.toString()

                        if (password == passwordFromFirebase) {
                            val sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(username_key, emaill)
                            editor.apply()

                            val home = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(home)
                        } else {
                            Toast.makeText(applicationContext, "password salah", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "username tidak ada", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "database error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}