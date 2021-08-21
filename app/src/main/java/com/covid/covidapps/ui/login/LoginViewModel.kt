package com.covid.covidapps.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.Result
import com.covid.covidapps.datasource.User
import com.covid.covidapps.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    val state: MutableStateFlow<Result<User>> =
        MutableStateFlow(Result.Init())

    @ExperimentalCoroutinesApi
    fun login(email: String, password: String) {
        state.value = Result.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.login(email, password)
                .catch {
                    state.value =
                        Result.Error("Login Error, Please Try Again")
                }
                .collect { user ->
                    if (user == null) {
                        state.value =
                            Result.Error("Login Error, Please Try Again")
                    } else {
                        state.value = Result.Success(data = user)
                    }
                }
        }
    }

    fun setLoginState(isLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.setLoginState(isLogin)
        }
    }

}