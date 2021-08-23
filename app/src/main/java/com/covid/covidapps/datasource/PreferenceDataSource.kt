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

    fun saveUserInfo(user: User){
        with(preferences.edit()){
            putString(USER_NAME_KEY, user.userName)
            putString(USER_EMAIL_KEY, user.userEmail)
            putString(USER_MOBILE_NO_KEY, user.userMobileNo)
            putString(USER_IMAGE_KEY, user.userImage)
            apply()
        }
    }

    fun getUserInfo(): User{
        val userName = preferences.getString(USER_NAME_KEY, "-")
        val userEmail = preferences.getString(USER_EMAIL_KEY, "-")
        val userMobileNo = preferences.getString(USER_MOBILE_NO_KEY, "-")
        val userImage = preferences.getString(USER_IMAGE_KEY, "-")
        return User(
            userName = userName ?: "-",
            userEmail = userEmail ?: "-",
            userMobileNo = userMobileNo ?: "-",
            userImage = userImage ?: "-"
        )
    }

    companion object {
        private const val LOGIN_STATE_KEY = "login_state_key"
        private const val USER_NAME_KEY = "user_name_key"
        private const val USER_EMAIL_KEY = "user_email_key"
        private const val USER_MOBILE_NO_KEY = "user_mobile_no_key"
        private const val USER_IMAGE_KEY = "user_image_key"
    }

}