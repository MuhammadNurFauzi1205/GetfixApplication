package com.example.getfixapplication.ui.auth.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.ui.home.NavigationHomeActivity
import com.example.getfixapplication.utils.showToast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
//    private lateinit var myreference : DatabaseReference
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
        val username = binding.ed1.editText?.text.toString()
        val email = binding.ed2.editText?.text.toString()
        val nama = binding.edtSignupNama.editText?.text.toString()
        val password = binding.ed3.editText?.text.toString()
        val dbcoll = db.collection("users")

        binding.btnSign.setOnClickListener {
            //simpan username pada local
            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(email_key, username)
            editor.apply()
            val user = hashMapOf(
                "nama" to nama,
                "username" to username,
                "email" to email,
                "password" to password
            )
            // Check if username is already taken
            dbcoll.get().addOnSuccessListener {
                for (document in it) {
                    if (document.get("username") == username) {
                        showToast(this, "Username already taken")
                        return@addOnSuccessListener
                    } else {
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                val home = Intent(this, NavigationHomeActivity::class.java)
                                startActivity(home)
                                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                }
            }


        }


        binding.tvActionLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }
    }
}