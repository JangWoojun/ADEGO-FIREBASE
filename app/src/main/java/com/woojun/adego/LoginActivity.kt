package com.woojun.adego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.adego.databinding.ActivityLoginBinding
import com.woojun.adego.profile.ProfileNameActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.googleLoginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ProfileNameActivity::class.java))
        }

        binding.kakaoLoginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ProfileNameActivity::class.java))
        }
    }
}