package com.example.getfixapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.FragmentHomeBinding
import com.example.getfixapplication.ui.booking.BookingActivity
import com.example.getfixapplication.ui.notifications.NotificationActivity
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN


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

        val imageList = ArrayList<SlideModel>() // Create image list
        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.carousel1))
        imageList.add(SlideModel(R.drawable.carousel2))
        imageList.add(SlideModel(R.drawable.carousel3))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)

        binding.ivHomeLaptop.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Laptop")
            startActivity(intent)
        }

        binding.ivHomeHandphone.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Handphone")
            startActivity(intent)
        }

        binding.ivHomeKomputer.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Komputer")
            startActivity(intent)
        }

        binding.ivHomeTelevisi.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra(USER_LAYANAN, "Televisi")
            startActivity(intent)
        }

        binding.ivHomeNotif.setOnClickListener {
            val intent = Intent(context, NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}