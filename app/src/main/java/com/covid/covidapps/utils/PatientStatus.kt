package com.covid.covidapps.utils

import com.covid.covidapps.R

enum class PatientStatus(val image: Int, val status: String) {
    RINGAN(R.drawable.ic_ringan, "Ringan"),
    SEDANG(R.drawable.ic_sedang, "Sedang"),
    BERAT(R.drawable.ic_berat, "Berat"),
    KRITIS(R.drawable.ic_kritis, "Kritis"),
    TANPA_GEJALA(R.drawable.ic_tanpa_gejala, "Tanpa Gejala")
}