package com.covid.covidapps.utils

import com.covid.covidapps.R

enum class PatientStatus(val image: Int) {
    RINGAN(R.drawable.ic_ringan),
    SEDANG(R.drawable.ic_sedang),
    BERAT(R.drawable.ic_berat),
    KRITIS(R.drawable.ic_kritis),
    TANPA_GEJALA(R.drawable.ic_tanpa_gejala)
}