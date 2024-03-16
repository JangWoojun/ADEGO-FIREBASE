package com.woojun.adego

import java.time.Duration
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.woojun.adego.dataClass.DateComparison
import com.woojun.adego.databinding.ActivityMainBinding
import com.woojun.adego.promise.PromiseNameActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        this@MainActivity.setStatusBarTransparent()

        if (AppPreferences.promiseDate != "" && compareDates(AppPreferences.promiseDate) != DateComparison.AFTER) {
            binding.nameText.text = AppPreferences.promiseName
            binding.dateText.text = AppPreferences.promiseDate
            binding.timeText.text = AppPreferences.promiseTime
            binding.locationText.text = AppPreferences.promiseLocation
            if (compareTime(AppPreferences.promiseTime) == DateComparison.BEFORE) {
                binding.nextText.text = calculateTimeDifference("${AppPreferences.promiseDate} ${AppPreferences.promiseTime}")
                binding.nextText.setTextColor(Color.parseColor("#525264"))
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#181C22"))
                binding.nextIcon.visibility = View.GONE
            } else if (compareTime(AppPreferences.promiseTime) == DateComparison.SAME) {
                binding.nextText.text = "알림 울리러 가기"
                binding.nextText.setTextColor(Color.parseColor("#000000"))
                binding.nextIcon.visibility = View.VISIBLE
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
                binding.sharedButton.visibility = View.GONE

                binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        startActivity(Intent(this@MainActivity, AlarmActivity::class.java))
                    }
                })
            } else {
                binding.nextText.text = "약속 생성하기"
                binding.nextText.setTextColor(Color.parseColor("#000000"))
                binding.nextIcon.visibility = View.VISIBLE
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
                binding.nonePromiseText.visibility = View.VISIBLE
                binding.promiseBox.visibility = View.GONE
                binding.sharedButton.visibility = View.GONE

                binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        startActivity(Intent(this@MainActivity, PromiseNameActivity::class.java))
                    }
                })
            }
        } else {
            binding.nextText.text = "약속 생성하기"
            binding.nextText.setTextColor(Color.parseColor("#000000"))
            binding.nextIcon.visibility = View.VISIBLE
            binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
            binding.nonePromiseText.visibility = View.VISIBLE
            binding.promiseBox.visibility = View.GONE
            binding.sharedButton.visibility = View.GONE

            binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View?) {
                    startActivity(Intent(this@MainActivity, PromiseNameActivity::class.java))
                }
            })
        }

        binding.settingButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
        })


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

    override fun onResume() {
        super.onResume()
        if (AppPreferences.promiseDate != "" && compareDates(AppPreferences.promiseDate) != DateComparison.AFTER) {
            binding.nameText.text = AppPreferences.promiseName
            binding.dateText.text = AppPreferences.promiseDate
            binding.timeText.text = AppPreferences.promiseTime
            binding.locationText.text = AppPreferences.promiseLocation
            if (compareTime(AppPreferences.promiseTime) == DateComparison.BEFORE) {
                binding.nextText.text = calculateTimeDifference("${AppPreferences.promiseDate} ${AppPreferences.promiseTime}")
                binding.nextText.setTextColor(Color.parseColor("#525264"))
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#181C22"))
                binding.nextIcon.visibility = View.GONE
            } else if (compareTime(AppPreferences.promiseTime) == DateComparison.SAME) {
                binding.nextText.text = "알림 울리러 가기"
                binding.nextText.setTextColor(Color.parseColor("#000000"))
                binding.nextIcon.visibility = View.VISIBLE
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
                binding.sharedButton.visibility = View.GONE

                binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        startActivity(Intent(this@MainActivity, AlarmActivity::class.java))
                    }
                })
            } else {
                binding.nextText.text = "약속 생성하기"
                binding.nextText.setTextColor(Color.parseColor("#000000"))
                binding.nextIcon.visibility = View.VISIBLE
                binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
                binding.nonePromiseText.visibility = View.VISIBLE
                binding.promiseBox.visibility = View.GONE
                binding.sharedButton.visibility = View.GONE

                binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        startActivity(Intent(this@MainActivity, PromiseNameActivity::class.java))
                    }
                })
            }
        } else {
            binding.nextText.text = "약속 생성하기"
            binding.nextText.setTextColor(Color.parseColor("#000000"))
            binding.nextIcon.visibility = View.VISIBLE
            binding.nextButton.setCardBackgroundColor(Color.parseColor("#F0F0F9"))
            binding.nonePromiseText.visibility = View.VISIBLE
            binding.promiseBox.visibility = View.GONE
            binding.sharedButton.visibility = View.GONE

            binding.nextButton.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View?) {
                    startActivity(Intent(this@MainActivity, PromiseNameActivity::class.java))
                }
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json
                )
            )
            if (!success) {
                Toast.makeText(this@MainActivity, "지도 불러오기 실패 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Resources.NotFoundException) {
            Toast.makeText(this@MainActivity, "지도 불러오기 실패 다시 시도해주세요", Toast.LENGTH_SHORT).show()
        }

        if (AppPreferences.promiseLatitude != "") {
            val marker = LatLng(AppPreferences.promiseLatitude.toDouble(), AppPreferences.promiseLongitude.toDouble())
            mMap.addMarker(
                MarkerOptions()
                    .position(marker)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18.0F))
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.570454631, 126.992134289), 17.0F))
        }
    }

    private fun Activity.setStatusBarTransparent() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun compareDates(dateString: String): DateComparison {
        val currentDate = LocalDate.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
        val targetDate = LocalDate.parse(dateString, formatter)

        return when {
            currentDate.isBefore(targetDate) -> DateComparison.BEFORE
            currentDate.isEqual(targetDate) -> DateComparison.SAME
            else -> DateComparison.AFTER
        }
    }


    private fun compareTime(inputTime: String): DateComparison {
        val currentTime = LocalTime.now()

        val formatter = DateTimeFormatter.ofPattern("a h시 m분")
        val targetTime = LocalTime.parse(inputTime, formatter)

        return when {
            currentTime.isBefore(targetTime.minusMinutes(1)) -> DateComparison.BEFORE
            currentTime.isAfter(targetTime.plusMinutes(21)) -> DateComparison.AFTER
            else -> DateComparison.SAME
        }
    }

    private fun calculateTimeDifference(targetDateTime: String): String {
        val currentDateTime = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분")
        val targetDateTimeObj = LocalDateTime.parse(targetDateTime, formatter)

        val duration = Duration.between(currentDateTime, targetDateTimeObj)

        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60

        val result = StringBuilder()

        if (days > 0) result.append("${days}일 ")
        if (hours > 0) result.append("${hours}시간 ")
        if (minutes > 0) result.append("${minutes}분")

        if (result.isEmpty()) {
            result.append("0분")
        }

        result.append(" 뒤 시작됩니다.")

        return result.toString()
    }
}