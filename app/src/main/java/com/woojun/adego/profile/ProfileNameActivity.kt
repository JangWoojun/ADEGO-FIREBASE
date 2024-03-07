package com.woojun.adego.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityProfileNameBinding

class ProfileNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityProfileNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
        }

        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nameLengthText.text = "사용자 이름 (${binding.nameInput.text.length}/8)"
            }

            override fun afterTextChanged(s: Editable?) {
                binding.nextButton.visibility = View.VISIBLE
                binding.noneButton.visibility = View.GONE
            }
        })

        binding.nameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                binding.nameInput.clearFocus()
                true
            } else {
                false
            }
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this@ProfileNameActivity, ProfileImageActivity::class.java).apply {
                this.putExtra("name", binding.nameInput.text)
            }
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }
}