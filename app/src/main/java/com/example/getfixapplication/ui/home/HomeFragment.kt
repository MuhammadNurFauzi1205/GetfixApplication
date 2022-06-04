package com.example.getfixapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.getfixapplication.databinding.FragmentHomeBinding
import com.example.getfixapplication.ui.booking.BookingActivity
import com.example.getfixapplication.ui.notifications.NotificationActivity
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN_GAMBAR


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivHomeLaptop.setOnClickListener {
            val intent = Intent(context,BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Laptop")
            startActivity(intent)
        }

        binding.ivHomeHandphone.setOnClickListener {
            val intent = Intent(context,BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Handphone")
            startActivity(intent)
        }

        binding.ivHomeKomputer.setOnClickListener {
            val intent = Intent(context,BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Komputer")
            startActivity(intent)
        }

        binding.ivHomeTelevisi.setOnClickListener {
            val intent = Intent(context,BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Televisi")
            startActivity(intent)
        }

        binding.ivHomeNotif.setOnClickListener {
            val intent = Intent(context,NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}