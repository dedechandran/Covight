package com.covid.covidapps.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.Result
import com.covid.covidapps.datasource.PreferenceDataSource
import com.covid.covidapps.datasource.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : ViewModel() {
    val uiState: MutableStateFlow<Result<User>> = MutableStateFlow(Result.Init())

    init {
        uiState.value = Result.Loading()
        viewModelScope.launch {
            try {
                val user = preferenceDataSource.getUserInfo()
                uiState.value = Result.Success(data = user)
            }catch (e: Exception){
                uiState.value = Result.Error("Something wrong")
            }
        }
    }
}