package com.woojun.adego

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.WindowCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.woojun.adego.databinding.ActivityMainBinding
import com.woojun.adego.promise.PromiseNameActivity

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap

    private var isPromise = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        this@MainActivity.setStatusBarTransparent()

        binding.settingButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }

        binding.nextButton.setOnClickListener {
            if (isPromise) {
                startActivity(Intent(this@MainActivity, PromiseNameActivity::class.java))
            } else {
                startActivity(Intent(this@MainActivity, AlarmActivity::class.java))
            }
        }

        binding.sharedButton.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                    type = "text/plain"
                    val url = ""
                    val content = "약속에 초대됐어요!\n하단 링크를 통해 어떤 약속인지 확인하세요."
                    putExtra(Intent.EXTRA_TEXT,"$content\n\n$url")
                }
            )
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

        val marker = LatLng(37.62759321424639, 126.92311223239273)
        mMap.addMarker(
            MarkerOptions()
            .position(marker)
            .title("세명컴퓨터고등학교"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18.0F))
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
}