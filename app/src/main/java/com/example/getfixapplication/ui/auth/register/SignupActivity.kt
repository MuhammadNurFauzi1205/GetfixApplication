package com.example.getfixapplication.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.ui.home.HomeActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivitySignupBinding
    var EMAIL_KEY = "emailkey"
    var email_key = ""

    lateinit var usernamee: TextInputLayout
    lateinit var passwordd: TextInputLayout
    lateinit var  emaill : TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernamee=binding.ed1
        emaill=binding.ed2
        passwordd=binding.ed3


        binding.btnSign.setOnClickListener {
            //simpan username pada local
            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(email_key, usernamee.editText.toString())
            editor.apply()


            //simpan username pada firebase
            myreference = FirebaseDatabase.getInstance().reference
                .child("users").child(usernamee.editText.toString())

            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("username").setValue(usernamee.editText.toString())
                    Log.d(dataSnapshot.child("username").toString(),"username:")
                    dataSnapshot.ref.child("email").setValue(emaill.editText.toString())
                    Log.d(dataSnapshot.child("email").toString(),"username:")
                    dataSnapshot.ref.child("password").setValue(passwordd.editText.toString())
                    Log.d(dataSnapshot.child("password").toString(),"username:")
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
//            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
        }

        binding.tvActionLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }
    }
}