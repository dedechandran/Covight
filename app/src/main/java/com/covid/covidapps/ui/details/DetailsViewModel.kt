package com.covid.covidapps.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.Result
import com.covid.covidapps.repository.PatientRepository
import com.covid.covidapps.ui.cardlist.CardItem
import com.covid.covidapps.utils.PatientStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    var uiState: MutableStateFlow<Result<List<CardItem>>> = MutableStateFlow(Result.Init())

    fun initialize(status: String) {
        viewModelScope.launch {
            uiState.value = Result.Loading()
            patientRepository.getPatient()
                .map {
                    it.filter { patient ->
                        patient.status?.name == status
                    }
                }
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
                .collect {
                    uiState.value = Result.Success(it)
                }

        }
    }
}