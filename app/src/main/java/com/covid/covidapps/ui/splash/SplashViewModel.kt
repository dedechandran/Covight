package com.covid.covidapps.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.Result
import com.covid.covidapps.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    val loginState: MutableStateFlow<Result<Boolean>> = MutableStateFlow(Result.Init())

    fun startNow(){
        loginState.value = Result.Loading()
        viewModelScope.launch {
            val isLogin = loginRepository.checkLogin()
            loginState.value = Result.Success(isLogin)
        }
    }
}