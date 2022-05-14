package com.example.getfixapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.getfixapplication.databinding.ActivityLoginBinding
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    var EMAIL_KEY = "emailkey"
    var email_key = ""
    lateinit var  username : EditText
    lateinit var passwordd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = binding.ed1
        passwordd = binding.ed2
        binding.btnLogin.setOnClickListener {


            myreference = FirebaseDatabase.getInstance().reference
                .child("users").child(username.text.toString())


            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        //ambil data password dari firebase
                        val passwordFromFirebase = dataSnapshot.child("password").value.toString()

                        //validasi password dengan firebase
                        if (passwordd.text.toString() == passwordFromFirebase) {

                            //simpan username pada local
                            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(email_key, username.text.toString())
                            editor.apply()
                            //berpindah activity
                            val two = Intent(this@LoginActivity , HomeActivity::class.java)
                            startActivity(two)
                        } else {
                            Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "email tidak ada", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Database error", Toast.LENGTH_SHORT).show()
                }
            })
        }
        binding.tvDaftar.setOnClickListener {
            val signup = Intent(this, SignupActivity::class.java)
            startActivity(signup)
        }


    }
}