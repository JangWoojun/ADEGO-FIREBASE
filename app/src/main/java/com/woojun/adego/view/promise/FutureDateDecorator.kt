package com.woojun.adego.view.promise

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.woojun.adego.R

class FutureDateDecorator(context: Context) : DayViewDecorator {
    private val color = ContextCompat.getColor(context, R.color.gray100)
    private val today = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.isAfter(today) || day == today
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(color))
    }
}
