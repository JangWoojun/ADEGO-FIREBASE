package com.woojun.adego.profile

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.woojun.adego.database.AppPreferences
import com.woojun.adego.MainActivity
import com.woojun.adego.R
import com.woojun.adego.User
import com.woojun.adego.databinding.ActivityProfileImageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileImageBinding
    private lateinit var database: DatabaseReference

    private var image: String = AppPreferences.profileImage

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        binding.noneButton.visibility = View.VISIBLE
                        binding.nextButton.visibility = View.GONE

                        if (!isDestroyed) {
                            Glide.with(this@ProfileImageActivity)
                                .load(it)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.imageView)
                        }
                    }
                    image = imageUpload(it)

                    withContext(Dispatchers.Main) {
                        binding.noneButton.visibility = View.GONE
                        binding.nextButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, R.anim.anim_fade_out)
        binding = ActivityProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference

        Glide.with(this@ProfileImageActivity)
            .load(image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView)

        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
        }

        binding.setImageButton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.setImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.nextButton.setOnClickListener {
            database.child("users").child(AppPreferences.id).setValue(User(AppPreferences.nickname, image))
            AppPreferences.isSignIn = true
            startActivity(Intent(this@ProfileImageActivity, MainActivity::class.java))
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, R.anim.anim_fade_out)
    }

    private suspend fun imageUpload(fileUri: Uri): String {
        try {
            val storage = Firebase.storage
            val storageRef = storage.reference

            val imageRef = storageRef.child("images/${AppPreferences.id}")
            val uploadTask = imageRef.putFile(fileUri)

            val taskSnapshot = uploadTask.await()
            val imageUrl = taskSnapshot.metadata?.reference?.downloadUrl?.await().toString()

            AppPreferences.profileImage = imageUrl

            return imageUrl
        } catch (e: Exception) {
            val defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/adego-6a7be.appspot.com/o/adego.png?alt=media&token=eb0056f3-35a9-4c7c-8304-2d93eed06803"
            AppPreferences.profileImage = defaultImageUrl
            return defaultImageUrl
        }
    }

}