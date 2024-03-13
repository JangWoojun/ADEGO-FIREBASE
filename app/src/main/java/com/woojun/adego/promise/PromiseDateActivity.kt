package com.woojun.adego.promise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.woojun.adego.databinding.ActivityPromiseDateBinding
import java.time.Instant


class PromiseDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromiseDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.addDecorators(PastDateDecorator(this), FutureDateDecorator(this))

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            startActivity(Intent(this@PromiseDateActivity, PromiseTimeActivity::class.java).apply {
                this.putExtra("date", date.date.toString())
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
}