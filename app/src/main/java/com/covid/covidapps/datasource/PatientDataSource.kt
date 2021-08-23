package com.covid.covidapps.datasource

import android.util.Log
import com.covid.covidapps.utils.PatientStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class PatientDataSource @Inject constructor(
    private val db: FirebaseDatabase,
    private val service: Service
) {

    @ExperimentalCoroutinesApi
    suspend fun getPatient(): Flow<Patient> {
        return callbackFlow {
            val ref = db.getReference("tools-1")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value as HashMap<*, *>
                    val patient = Patient(
                        FSR = (data["FSR"] as String).toDouble(),
                        heartRate = (data["HeartRate"] as String).toDouble(),
                        spO2 = (data["SpO2"] as String).toDouble(),
                        temperature = (data["Suhu"] as String).toDouble(),
                    )
                    offer(patient)
                    Log.d("DATA", "$data")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ERROR", error.message)
                }

            }
            ref.addValueEventListener(listener)
            awaitClose { ref.removeEventListener(listener) }
        }
    }

    suspend fun classify(patient: Patient): Flow<Patient> {
        val convertedValue = patient.convert()
        val x1 = 18.toString()
        val x2 = 1.toString()
        val x3 = convertedValue.temperature.toInt().toString()
        val x4 = convertedValue.FSR.toInt().toString()
        val x5 = convertedValue.spO2.toInt().toString()
        return flow {
            val result = service.classify(x1, x2, x3, x4, x5)
            emit(
                patient.copy(
                    status = when (result.toInt()) {
                        4 -> PatientStatus.KRITIS
                        3 -> PatientStatus.BERAT
                        2 -> PatientStatus.SEDANG
                        1 -> PatientStatus.RINGAN
                        0 -> PatientStatus.TANPA_GEJALA
                        else -> PatientStatus.TANPA_GEJALA
                    }
                )
            )
        }
    }
}