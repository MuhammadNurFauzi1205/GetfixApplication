package com.example.getfixapplication.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivityMainBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.ui.home.NavigationHomeActivity
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.timeSplash

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intentToSign = Intent(this@MainActivity, LoginActivity::class.java)
        val intentToHome = Intent(this@MainActivity, NavigationHomeActivity::class.java)
        val handler = Handler(mainLooper)
        sharedPreferences = getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)
        if (sharedPreferences.getString(USER_ID_SESSION, null) != null) {
            handler.postDelayed({
                startActivity(intentToHome)
                finish()
            }, timeSplash.toLong())
        } else {
            handler.postDelayed({
                startActivity(intentToSign)
                finish()
            }, timeSplash.toLong())
        }
    }
}