package com.covid.covidapps.ui.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.datasource.Patient
import com.covid.covidapps.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    val state: MutableStateFlow<Result<Patient>> = MutableStateFlow(Result.Init())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            patientRepository.getPatient().collect {
                state.value = Result.Success(it)
            }
        }
    }
}