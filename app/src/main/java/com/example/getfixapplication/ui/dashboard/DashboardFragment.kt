package com.example.getfixapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.FragmentDashboardBinding
import com.example.getfixapplication.ui.order.TabOrderAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabOrderAdapter = TabOrderAdapter(this)
        val viewPager= _binding?.viewPager
        viewPager?.adapter = tabOrderAdapter
        val tabs= _binding?.tabs
        if (tabs != null) {
            if (viewPager != null) {
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TabLayout = binding.tabs
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.tabMode
//        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}