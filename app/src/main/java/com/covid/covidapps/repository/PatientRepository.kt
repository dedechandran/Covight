package com.covid.covidapps.repository

import com.covid.covidapps.datasource.Patient
import com.covid.covidapps.datasource.PatientDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val dataSource: PatientDataSource
) {

    @ExperimentalCoroutinesApi
    suspend fun getPatient(): Flow<Patient>{
        return dataSource.getPatient()
    }

}