package com.example.getfixapplication.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivitySignupBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.utils.hideKeyboard
import com.example.getfixapplication.utils.isEmailValid
import com.example.getfixapplication.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    var EMAIL_KEY = "emailkey"
    var email_key = ""
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbcoll = db.collection("users")
        val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        auth = Firebase.auth

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
            } else if (!email.isEmailValid()) {
                binding.ed2.editText?.error = "Email tidak valid"
            } else {
                editor.putString(email_key, username)
                editor.apply()
                val xAuth = hashMapOf(
                    "nama" to nama,
                    "username" to username,
                    "email" to email,
                    "password" to password
                )

                dbcoll.get().addOnSuccessListener {
                    for (document in it) {
                        if (document.get("username") == username) {
                            showToast(this, "Username sudah terdaftar")
                            return@addOnSuccessListener
                        }
                    }
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { sAuth ->
                            if (sAuth.isSuccessful) {
                                Toast.makeText(this, "Berhasil mendaftar", Toast.LENGTH_SHORT)
                                    .show()
                                val user = auth.currentUser?.uid
                                if (user != null) {
                                    dbcoll.document(user).set(xAuth)
                                }
                                finish()
                            } else {
                                sAuth.exception?.message?.let { m ->
                                    showToast(this, m)
                                }
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