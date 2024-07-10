package com.woojun.adego.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.woojun.adego.BuildConfig
import com.woojun.adego.database.AppPreferences

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}