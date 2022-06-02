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
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tabOrderAdapter = TabOrderAdapter(this)
        val viewPager= binding.viewPager
        viewPager.adapter = tabOrderAdapter
        val tabs= binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }
}