package com.example.getfixapplication.ui.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.getfixapplication.databinding.FragmentProfileBinding
import com.example.getfixapplication.ui.auth.login.LoginActivity
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)!!
        val userId = sharedPreferences.getString(USER_ID_SESSION, null)
        if (userId != null) {
            profileViewModel.profileUser(userId).observe(viewLifecycleOwner) {
                binding.tvDescNama.text = it.nama
                binding.tvDescEmail.text = it.email
            }
        }
        binding.cvProfileLogout.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
    }

}