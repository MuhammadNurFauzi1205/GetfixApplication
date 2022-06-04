package com.example.getfixapplication.ui.home

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityNavigationHomeBinding
import com.example.getfixapplication.ui.camera.CameraActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView
        supportActionBar?.elevation = 0f
        val navController = findNavController(R.id.nav_host_fragment_activity_homee)
        navView.setupWithNavController(navController)

    }
}