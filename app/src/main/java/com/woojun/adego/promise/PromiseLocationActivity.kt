package com.woojun.adego.promise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.adego.BuildConfig
import com.woojun.adego.R
import com.woojun.adego.dataClass.KakaoLocation
import com.woojun.adego.dataClass.Location
import com.woojun.adego.databinding.ActivityPromiseLocationBinding
import com.woojun.adego.network.RetrofitAPI
import com.woojun.adego.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.nameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getLocation(this@PromiseLocationActivity, binding.nameInput.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }

    private fun getLocation(context: Context, query: String) {
        val retrofit = RetrofitClient.getInstance("https://dapi.kakao.com/v2/")
        val apiService = retrofit.create(RetrofitAPI::class.java)
        val call = apiService.getLocation("KakaoAK ${BuildConfig.REST_API_KEY}", query)

        call.enqueue(object : Callback<KakaoLocation> {
            override fun onResponse(call: Call<KakaoLocation>, response: Response<KakaoLocation>) {
                if (response.isSuccessful && response.body() != null) {
                    setRecyclerView(context, response.body()!!)
                } else {
                    Toast.makeText(context, "검색에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<KakaoLocation>, t: Throwable) {
                Toast.makeText(context, "검색에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView(context: Context, data: KakaoLocation) {
        val list = data.documents.map {
            Location(
                it.place_name,
                it.address_name,
                it.road_address_name,
                it.x,
                it.y
            )
        }.toMutableList()

        binding.locationList.layoutManager = LinearLayoutManager(context)
        binding.locationList.adapter = LocationAdapter(list)
    }
}