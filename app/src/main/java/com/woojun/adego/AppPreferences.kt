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
    var isSignIn: Boolean
        get() = preferences.getBoolean(ISSIGNIN.first, ISSIGNIN.second)
        set(value) = preferences.edit {
            it.putBoolean(ISSIGNIN.first, value)
        }
}