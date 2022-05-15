package com.example.getfixapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

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
                    dataSnapshot.ref.child("email").setValue(emaill.editText.toString())
                    dataSnapshot.ref.child("password").setValue(passwordd.editText.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
//            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
        }

        binding.tvDaftar.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }
    }
}