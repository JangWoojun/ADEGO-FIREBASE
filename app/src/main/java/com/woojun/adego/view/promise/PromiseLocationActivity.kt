package com.woojun.adego.view.promise

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.adego.BuildConfig
import com.woojun.adego.R
import com.woojun.adego.dataClass.KakaoLocation
import com.woojun.adego.dataClass.Location
import com.woojun.adego.dataClass.PromiseInfo
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

        val name = intent.getStringExtra("name")!!
        val date = intent.getStringExtra("date")!!
        val time = intent.getStringExtra("time")!!

        val promiseInfo = PromiseInfo(
            name,
            date,
            time
        )

        binding.backButton.setOnClickListener {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
            finish()
        }

        binding.nameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getLocation(this@PromiseLocationActivity, binding.nameInput.text.toString(), promiseInfo)
                true
            } else {
                false
            }
        }

        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getLocation(this@PromiseLocationActivity, binding.nameInput.text.toString(), promiseInfo)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }

    private fun getLocation(context: Context, query: String, promiseInfo: PromiseInfo) {
        val retrofit = RetrofitClient.getInstance("https://dapi.kakao.com/v2/")
        val apiService = retrofit.create(RetrofitAPI::class.java)
        val call = apiService.getLocation("KakaoAK ${BuildConfig.REST_API_KEY}", query)

        call.enqueue(object : Callback<KakaoLocation> {
            override fun onResponse(call: Call<KakaoLocation>, response: Response<KakaoLocation>) {
                if (response.isSuccessful && response.body() != null) {
                    setRecyclerView(context, response.body()!!, promiseInfo)
                }
            }
            override fun onFailure(call: Call<KakaoLocation>, t: Throwable) {
                Toast.makeText(context, "검색에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView(context: Context, data: KakaoLocation, promiseInfo: PromiseInfo) {
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
        binding.locationList.adapter = LocationAdapter(list, promiseInfo)
    }
}