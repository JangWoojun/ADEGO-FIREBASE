package com.woojun.adego.promise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.woojun.adego.database.AppPreferences
import com.woojun.adego.databinding.ActivityPromiseNameBinding

class PromiseNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPromiseNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromiseNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.nameInput.setText(AppPreferences.nickname)

        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nameLengthText.text = "약속 이름 (${binding.nameInput.text.length}/12)"
                if (binding.nameInput.text.isNotEmpty()) {
                    binding.nextButton.visibility = View.VISIBLE
                    binding.noneButton.visibility = View.GONE
                } else {
                    binding.nextButton.visibility = View.GONE
                    binding.noneButton.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
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
            startActivity(
                Intent(this@PromiseNameActivity, PromiseDateActivity::class.java).apply {
                    this.putExtra("name", binding.nameInput.text.toString())
                }
            )
        }
    }
}