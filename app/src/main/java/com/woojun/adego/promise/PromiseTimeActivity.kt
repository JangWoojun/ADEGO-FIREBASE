package com.woojun.adego.promise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityPromiseTimeBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PromiseTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityPromiseTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            finish()
        }

        binding.dateText.text = if (date != null) {
            "${convertDateFormat(date.toString())}"
        } else {
            "날짜 오류"
        }

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this@PromiseTimeActivity, PromiseLocationActivity::class.java).apply {
                this.putExtra("name", name)
                this.putExtra("date", date)
                this.putExtra("time", "${binding.timeView.hour} ${binding.timeView.minute}")
            })
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }

    private fun convertDateFormat(inputDate: String, inputFormat: String = "yyyy-MM-dd", outputFormat: String = "yyyy년 M월 d일"): String {
        val formatter = DateTimeFormatter.ofPattern(inputFormat)
        val date = LocalDate.parse(inputDate, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern(outputFormat)
        return date.format(outputFormatter)
    }
}