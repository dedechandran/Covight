package com.covid.covidapps.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.R
import com.covid.covidapps.Result
import com.covid.covidapps.repository.PatientRepository
import com.covid.covidapps.ui.cardlist.CardItem
import com.covid.covidapps.utils.PatientStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val patientRepository: PatientRepository
): ViewModel(){

    val state: MutableStateFlow<Result<List<CardItem>>> = MutableStateFlow(Result.Init())

    init {
        state.value = Result.Loading()
        viewModelScope.launch {
            patientRepository.getPatient()
                .map {
                    CardItem.PatientSummary(
                        id = "",
                        image = R.drawable.ic_baseline_account_circle_24,
                        status = it.status ?: PatientStatus.TANPA_GEJALA,
                        total = "1 Orang"
                    )
                }
                .catch { throwable ->
                    Log.d("ERROR", throwable.stackTraceToString())
                    state.value = Result.Error(message = "There is something wrong")
                }
                .collect {
                    state.value = Result.Success(listOf(it))
                }
        }
    }

}