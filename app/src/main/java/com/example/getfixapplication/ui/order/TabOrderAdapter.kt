package com.example.getfixapplication.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getfixapplication.ui.dashboard.DashboardFragment


class TabOrderAdapter(activity: DashboardFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MyOrderFragment()
            1 -> fragment = FinishorderFragment()
        }
        return fragment as Fragment
    }

}