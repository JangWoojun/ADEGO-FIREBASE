package com.woojun.adego.promise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.woojun.adego.database.AppPreferences
import com.woojun.adego.MainActivity
import com.woojun.adego.OnSingleClickListener
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityPromiseFinishBinding

class PromiseFinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityPromiseFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            startActivity(Intent(this@PromiseFinishActivity, MainActivity::class.java))
            finish()
        }

        binding.nameText.text = AppPreferences.promiseName
        binding.dateText.text = AppPreferences.promiseDate
        binding.timeText.text = AppPreferences.promiseTime
        binding.locationText.text = AppPreferences.promiseLocation

        binding.finishButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            startActivity(Intent(this@PromiseFinishActivity, MainActivity::class.java))
            finish()
        }

        binding.sharedButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                startActivity(
                    Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                        type = "text/plain"
                        val url = ""
                        val content = "약속에 초대됐어요!\n하단 링크를 통해 어떤 약속인지 확인하세요."
                        putExtra(Intent.EXTRA_TEXT,"$content\n\n$url")
                    }
                )
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }
}