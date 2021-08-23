package com.covid.covidapps.datasource

import com.covid.covidapps.utils.PatientStatus

data class Patient(
    val FSR: Double,
    val heartRate: Double,
    val spO2: Double,
    val temperature: Double,
    val status: PatientStatus? = null
)


fun Patient.convert() = Patient(
    FSR = when {
        FSR < 16 -> 8.toDouble()
        FSR < 20 -> 10.toDouble()
        FSR < 30 -> 6.toDouble()
        FSR < 40 -> 4.toDouble()
        FSR < 50 -> 2.toDouble()
        FSR < 59 -> 0.toDouble()
        FSR > 16 -> 9.toDouble()
        FSR > 20 -> 11.toDouble()
        FSR > 30 -> 7.toDouble()
        FSR > 40 -> 5.toDouble()
        FSR > 50 -> 3.toDouble()
        FSR > 59 -> 1.toDouble()
        else -> 0.toDouble()
    },
    temperature = if(temperature > 37){
        0.toDouble()
    }else{
        1.toDouble()
    },
    heartRate = heartRate,
    spO2 = when{
        spO2 < 28 -> 12.toDouble()
        spO2 < 90 -> 2.toDouble()
        spO2 in 90.0..95.0 -> 1.toDouble()
        spO2 > 95 -> 0.toDouble()
        spO2 > 28 -> 13.toDouble()
        else -> 0.toDouble()
    }
)
