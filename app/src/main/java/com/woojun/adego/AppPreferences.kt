package com.woojun.adego

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "AppPreferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val ID = Pair("id", "")
    private val NICKNAME = Pair("nickname", "")
    private val PROFILEImage = Pair("profile", "")

    private val PROMISENAME = Pair("promiseName", "")
    private val PROMISETIME = Pair("promiseTime", "")
    private val PROMISEDATE = Pair("promiseDate", "")
    private val PROMISELOCATION = Pair("promiseLocation", "")
    private val PROMISELONGITUDE = Pair("promiseLongitude", "")
    private val PROMISELATIUDE = Pair("promiseLatitude", "")

    private val ACCESS_TOKEN = Pair("accessToken", "")
    private val REFRESH_TOKEN = Pair("refreshToken", "")
    private val ISSIGNIN = Pair("isSignIn", false)


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var id: String
        get() = preferences.getString(ID.first, ID.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ID.first, value)
        }
    var nickname: String
        get() = preferences.getString(NICKNAME.first, NICKNAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(NICKNAME.first, value)
        }
    var profileImage: String
        get() = preferences.getString(PROFILEImage.first, PROFILEImage.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROFILEImage.first, value)
        }

    var promiseName: String
        get() = preferences.getString(PROMISENAME.first, PROMISENAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISENAME.first, value)
        }
    var promiseTime: String
        get() = preferences.getString(PROMISETIME.first, PROMISETIME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISETIME.first, value)
        }
    var promiseDate: String
        get() = preferences.getString(PROMISEDATE.first, PROMISEDATE.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISEDATE.first, value)
        }
    var promiseLocation: String
        get() = preferences.getString(PROMISELOCATION.first, PROMISELOCATION.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISELOCATION.first, value)
        }
    var promiseLongitude: String
        get() = preferences.getString(PROMISELONGITUDE.first, PROMISELONGITUDE.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISELONGITUDE.first, value)
        }
    var promiseLatitude: String
        get() = preferences.getString(PROMISELATIUDE.first, PROMISELATIUDE.second) ?: ""
        set(value) = preferences.edit {
            it.putString(PROMISELATIUDE.first, value)
        }

    var isSignIn: Boolean
        get() = preferences.getBoolean(ISSIGNIN.first, ISSIGNIN.second)
        set(value) = preferences.edit {
            it.putBoolean(ISSIGNIN.first, value)
        }
    var accessToken: String
        get() = preferences.getString(ACCESS_TOKEN.first, ACCESS_TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ACCESS_TOKEN.first, value)
        }
    var refreshToken: String
        get() = preferences.getString(REFRESH_TOKEN.first, REFRESH_TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(REFRESH_TOKEN.first, value)
        }
}