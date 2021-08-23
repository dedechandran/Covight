package com.covid.covidapps.repository

import com.covid.covidapps.datasource.Patient
import com.covid.covidapps.datasource.PatientDataSource
import com.covid.covidapps.datasource.convert
import com.covid.covidapps.utils.PatientStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val dataSource: PatientDataSource
) {

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun getPatient(): Flow<Patient>{
        return dataSource.getPatient()
            .flatMapConcat {
                dataSource.classify(it)
            }
            .flowOn(Dispatchers.IO)
    }

}