package com.woojun.adego.view.promise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityPromiseDateBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern


class PromiseDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityPromiseDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            finish()
        }

        binding.calendarView.addDecorators(PastDateDecorator(this), FutureDateDecorator(this))

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            startActivity(Intent(this@PromiseDateActivity, PromiseTimeActivity::class.java).apply {
                this.putExtra("date", convertDateFormat(date.date.toString()))
                this.putExtra("name", name)
            })
        }

        binding.calendarView.setTitleFormatter { day ->
            val inputText = day.date

            val calendarHeaderElements = inputText.toString().split("-")
            val calendarHeaderBuilder = StringBuilder()

            calendarHeaderBuilder.append(calendarHeaderElements[0]).append("년 ")
                .append(calendarHeaderElements[1]).append("월")

            calendarHeaderBuilder.toString()
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