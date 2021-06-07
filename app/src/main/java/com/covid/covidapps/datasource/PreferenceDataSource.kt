package com.covid.covidapps.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    private val preferences: SharedPreferences
) {

    fun setLoginState(isLogin: Boolean = false){
        with(preferences.edit()){
            putBoolean(LOGIN_STATE_KEY, isLogin)
            apply()
        }
    }

    fun getLoginState(): Boolean{
        return preferences.getBoolean(LOGIN_STATE_KEY, false)
    }

    companion object {
        private const val LOGIN_STATE_KEY = "login_state_key"
    }

}