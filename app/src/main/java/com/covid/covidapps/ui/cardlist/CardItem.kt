package com.covid.covidapps.ui.cardlist

import com.covid.covidapps.datasource.Patient
import com.covid.covidapps.utils.PatientStatus

sealed class CardItem {
    data class PatientDetails(
        val id: String,
        val patientDetails: Patient?,
        val status: PatientStatus,
        val name: String,
        val roomName: String
    ) : CardItem()

    data class PatientSummary(
        val id: String,
        val image: Int,
        val status: PatientStatus,
        val total: String?
    ) : CardItem()

    data class News(
        val id: String,
        val image: Int,
        val date: String,
        val title: String
    ) : CardItem()
}