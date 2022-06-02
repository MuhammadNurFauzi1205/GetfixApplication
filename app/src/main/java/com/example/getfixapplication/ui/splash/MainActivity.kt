package com.example.getfixapplication.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.example.getfixapplication.databinding.ActivityMainBinding
import com.example.getfixapplication.ui.auth.register.SignupActivity
import com.example.getfixapplication.ui.home.HomeeActivity
import com.example.getfixapplication.utils.ConstVal.timeSplash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intentToSignup = Intent(this@MainActivity, HomeeActivity::class.java)
        val handler = Handler(mainLooper)

        lifecycleScope.launch(Dispatchers.Default) {
            handler.postDelayed({
                startActivity(intentToSignup)
                finish()
            }, timeSplash.toLong())
        }
        }



}