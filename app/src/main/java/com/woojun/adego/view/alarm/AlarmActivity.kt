package com.woojun.adego.view.alarm

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.woojun.adego.dataClass.User
import com.woojun.adego.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private val list = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.friendsList.layoutManager = GridLayoutManager(this@AlarmActivity, 2)
        binding.friendsList.adapter = AlarmAdapter(list)

        binding.friendsList.addItemDecoration(EdgeItemDecoration(2, 8f.fromDpToPx()))
    }

    private fun Float.fromDpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()
}