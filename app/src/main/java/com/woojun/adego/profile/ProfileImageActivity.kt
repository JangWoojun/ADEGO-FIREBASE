package com.woojun.adego.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.woojun.adego.MainActivity
import com.woojun.adego.R
import com.woojun.adego.databinding.ActivityProfileImageBinding
import java.io.ByteArrayOutputStream

class ProfileImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileImageBinding
    private var image: String = ""

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                image = uriToBase64(this@ProfileImageActivity, it)
                Glide.with(this@ProfileImageActivity)
                    .load(it)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageView)
                binding.nextButton.visibility = View.VISIBLE
                binding.noneButton.visibility = View.GONE
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
        }

        binding.setImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this@ProfileImageActivity, MainActivity::class.java))
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }

    private fun uriToBase64(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()

        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }
        inputStream?.close()

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }
}