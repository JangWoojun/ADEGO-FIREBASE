package com.woojun.adego.view.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.woojun.adego.database.AppPreferences
import com.woojun.adego.databinding.ActivityLoginBinding
import com.woojun.adego.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        binding.googleLoginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ProfileNameActivity::class.java))
        }

        binding.kakaoLoginButton.setOnClickListener {
            kakaoLogin(this@LoginActivity)
        }
    }

    private fun kakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(context, "카카오 로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                kakaoGetUserProfile(context)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    kakaoGetUserProfile(context)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun kakaoGetUserProfile(context: Context) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Toast.makeText(context, "카카오 로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
            else if (user?.id != null) {
                kakaoSignUp(context, user.id.toString(), user.kakaoAccount!!.profile!!.nickname!!, user.kakaoAccount!!.profile!!.thumbnailImageUrl!!)
            }
        }
    }

    private fun kakaoSignUp(context: Context, userId: String, userNickname: String, userProfileImage: String) {
        AppPreferences.id = userId
        AppPreferences.nickname = userNickname
        AppPreferences.profileImage = userProfileImage

        database.child("users").child(userId).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                AppPreferences.isSignIn = true
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                startActivity(
                    Intent(this@LoginActivity, ProfileNameActivity::class.java).apply {
                        putExtra("isSignUp", true)
                    }
                )
            }
        }.addOnFailureListener {
            Toast.makeText(context, "카카오 로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
        }

    }

}