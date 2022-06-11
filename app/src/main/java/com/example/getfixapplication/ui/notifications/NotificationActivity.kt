package com.example.getfixapplication.ui.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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