package com.woojun.adego

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}