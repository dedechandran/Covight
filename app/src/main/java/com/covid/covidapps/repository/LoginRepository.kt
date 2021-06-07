package com.covid.covidapps.repository

import com.covid.covidapps.datasource.AuthDataSource
import com.covid.covidapps.datasource.PreferenceDataSource
import com.covid.covidapps.datasource.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val preferenceDataSource: PreferenceDataSource
) {

    @ExperimentalCoroutinesApi
    suspend fun login(email: String, password: String): Flow<User?> {
        return authDataSource.login(email, password)
    }

    suspend fun checkLogin(): Boolean {
        return preferenceDataSource.getLoginState()
    }

    suspend fun setLoginState(isLogin: Boolean){
        preferenceDataSource.setLoginState(isLogin)
    }

}