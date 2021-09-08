package com.covid.covidapps.ui

import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covid.covidapps.Result
import com.covid.covidapps.repository.PatientRepository
import com.covid.covidapps.ui.cardlist.CardItem
import com.covid.covidapps.utils.PatientStatus
import com.covid.covidapps.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private var isInitialize = false
    val notificationEvent = SingleLiveEvent<String>()

    @FlowPreview
    fun initialze() {
        if (isInitialize) return
        viewModelScope.launch {
            patientRepository.getPatient()
                .catch {
                }
                .collect {
                    val hasKritisStatus = it.find { it.status == PatientStatus.KRITIS }
                    if (hasKritisStatus != null) {
                        notificationEvent.value = "Terdapat pasien kritis"
                    }
                }
        }
        isInitialize = true
    }

}