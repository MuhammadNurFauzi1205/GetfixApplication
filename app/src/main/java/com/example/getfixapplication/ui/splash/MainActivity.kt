package com.example.getfixapplication.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.getfixapplication.databinding.ActivityMainBinding
import com.example.getfixapplication.ui.auth.register.SignupActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

            handler = Handler()
            handler.postDelayed({

                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()

            }, 3000)
        }



}