package com.example.getfixapplication.ui.auth.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.ui.home.HomeActivity
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivitySignupBinding
    var EMAIL_KEY = "emailkey"
    var email_key = ""
    val db = Firebase.firestore

//    lateinit var usernamee: TextInputLayout
//    lateinit var passwordd: TextInputLayout
//    lateinit var  emaill : TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernamee=binding.ed1
        val emaill=binding.ed2
        val passwordd=binding.ed3

        binding.btnSign.setOnClickListener {
            //simpan username pada local
            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(email_key, usernamee.editText?.text.toString())
            editor.apply()
//
//
//            //simpan username pada firebase
//            myreference = FirebaseDatabase.getInstance().reference
//                .child("users").child(usernamee.editText?.text.toString())
//
//            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    dataSnapshot.ref.child("username").setValue(usernamee.editText?.text.toString())
//                    dataSnapshot.ref.child("email").setValue(emaill.editText?.text.toString())
//                    dataSnapshot.ref.child("password").setValue(passwordd.editText?.text.toString())
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {}
//            })
//            val home = Intent(this, HomeActivity::class.java)
//            startActivity(home)
////            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()

            val username = usernamee.editText?.text.toString()
            val email = emaill.editText?.text.toString()
            val password = passwordd.editText?.text.toString()
            val user = hashMapOf(
                "username" to username,
                "email" to email,
                "password" to password
            )
            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    val home = Intent(this, HomeActivity::class.java)
                    startActivity(home)
                    Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }


        }

        binding.tvActionLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }
    }
}