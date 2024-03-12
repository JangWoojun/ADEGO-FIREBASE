package com.woojun.adego.promise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityPromiseDateBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class PromiseDateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromiseDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.addDecorators(PastDateDecorator(this), FutureDateDecorator(this), )

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            Toast.makeText(this@PromiseDateActivity, date.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}