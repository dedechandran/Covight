package com.covid.covidapps.ui.cardlist

import com.covid.covidapps.datasource.Patient

sealed class CardItem {
    data class PatientDetails(
        val id: String,
        val image: Int,
        val patientDetails: Patient?,
        val status: String,
        val name: String,
        val roomName: String
    ) : CardItem()

    data class PatientSummary(
        val id: String,
        val image: Int,
        val status: String,
        val total: String?
    ) : CardItem()

    data class News(
        val id: String,
        val image: Int,
        val date: String,
        val title: String
    ) : CardItem()
}