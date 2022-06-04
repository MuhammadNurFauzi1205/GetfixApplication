package com.example.getfixapplication.ui.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationBinding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)

        notificationBinding.tbNotificationToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}