package com.covid.covidapps.ui.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.repository.PatientRepository
import com.covid.covidapps.Result
import com.covid.covidapps.ui.cardlist.CardItem
import com.covid.covidapps.utils.PatientStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    val state: MutableStateFlow<Result<List<CardItem>>> = MutableStateFlow(Result.Init())

    init {
        state.value = Result.Loading()
        viewModelScope.launch {
            patientRepository.getPatient()
                .map {
                    it.map { patient ->
                        CardItem.PatientDetails(
                            id = patient.id.toString(),
                            patientDetails = patient,
                            status = patient.status ?: PatientStatus.TANPA_GEJALA,
                            name = patient.patientName,
                            roomName = "Room 1"
                        )
                    }
                }
                .catch {
                    state.value = Result.Error(message = "There is something wrong")
                }
                .collect {
                    state.value = Result.Success(it)
                }
        }
    }
}