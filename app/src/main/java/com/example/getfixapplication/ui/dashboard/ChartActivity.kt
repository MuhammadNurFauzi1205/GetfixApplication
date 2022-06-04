package com.example.getfixapplication.ui.dashboard

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.FragmentDashboardBinding
import com.example.getfixapplication.ui.Order.TabOrderAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    private lateinit var binding : FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}