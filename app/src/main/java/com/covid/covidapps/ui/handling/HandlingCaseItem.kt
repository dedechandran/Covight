package com.covid.covidapps.ui.handling

import com.covid.covidapps.utils.PatientStatus

data class HandlingCaseItem(
    val id: String,
    val action: String,
    val image: Int,
    val status: PatientStatus
)