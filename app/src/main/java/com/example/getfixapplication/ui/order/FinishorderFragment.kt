package com.example.getfixapplication.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getfixapplication.databinding.FragmentFinishorderBinding

class FinishorderFragment : Fragment() {

    private lateinit var binding: FragmentFinishorderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishorderBinding.inflate(inflater, container, false)
        return binding.root
    }

}