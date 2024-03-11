package com.woojun.adego

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.woojun.adego.databinding.ActivitySettingBinding
import com.woojun.adego.profile.ProfileNameActivity


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private var image: String = AppPreferences.profileImage
    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                image = it.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.logoutButton.paintFlags = binding.logoutButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG;
        binding.logoutButton.setOnClickListener {
            val customDialog = Dialog(this@SettingActivity)
            customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

            customDialog.setContentView(R.layout.dialog_setting)

            customDialog.findViewById<TextView>(R.id.main_button_text).text = "로그아웃"
            customDialog.findViewById<CardView>(R.id.main_button).setOnClickListener {
                finish()
            }
            customDialog.findViewById<CardView>(R.id.cancel_button).setOnClickListener {
                customDialog.cancel()
            }

            customDialog.show()
        }

        binding.withdrawalButton.paintFlags = binding.withdrawalButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG;
        binding.withdrawalButton.setOnClickListener {
            val customDialog = Dialog(this@SettingActivity)
            customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

            customDialog.setContentView(R.layout.dialog_setting)

            customDialog.findViewById<TextView>(R.id.main_button_text).text = "탈퇴"
            customDialog.findViewById<CardView>(R.id.main_button).setOnClickListener {
                finish()
            }
            customDialog.findViewById<CardView>(R.id.cancel_button).setOnClickListener {
                customDialog.cancel()
            }

            customDialog.show()
        }

        binding.changeImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

    }
}