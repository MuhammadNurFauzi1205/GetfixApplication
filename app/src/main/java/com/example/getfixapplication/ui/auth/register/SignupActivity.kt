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
import com.example.getfixapplication.utils.hideKeyboard
import com.example.getfixapplication.utils.isEmailValid
import com.example.getfixapplication.utils.showToast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    var EMAIL_KEY = "emailkey"
    var email_key = ""
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbcoll = db.collection("users")
        val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.btnSign.setOnClickListener {
            //simpan username pada local
            hideKeyboard()

            val username = binding.ed1.editText?.text.toString()
            val email = binding.ed2.editText?.text.toString()
            val nama = binding.edtSignupNama.editText?.text.toString()
            val password = binding.ed3.editText?.text.toString()

            // Check if username, email, nama, and password is empty
            if (username.isEmpty() || email.isEmpty() || nama.isEmpty() || password.isEmpty()) {
                showToast(this, "Mohon untuk mengisi semua field")
            } else if (password.length < 6) {
                showToast(this, "Password harus lebih dari 6 karakter")
            } else if (!email.isEmailValid()){
                binding.ed2.editText?.error = "Email tidak valid"
            } else {
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
                        } else if (document.get("email") == email) {
                            showToast(this, "Email already taken")
                            return@addOnSuccessListener
                        }
                    }
                    dbcoll.add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            val login = Intent(this, LoginActivity::class.java)
                            startActivity(login)
                            showToast(this, "Register Berhasil")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
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