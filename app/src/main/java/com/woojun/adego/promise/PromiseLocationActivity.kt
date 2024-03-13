package com.woojun.adego.promise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityPromiseLocationBinding

class PromiseLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityPromiseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }
}