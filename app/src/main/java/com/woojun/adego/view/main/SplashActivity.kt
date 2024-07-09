package com.woojun.adego.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.woojun.adego.view.auth.LoginActivity
import com.woojun.adego.R
import com.woojun.adego.database.AppPreferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (AppPreferences.isSignIn) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finishAffinity()
        }, 2000)
    }
}