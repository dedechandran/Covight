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
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientDataSource @Inject constructor(
    private val db: FirebaseDatabase,
    private val service: Service
) {

    @ExperimentalCoroutinesApi
    suspend fun getPatient(): Flow<List<Patient>> {
        return callbackFlow {
            val ref = db.reference
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val patientRef = snapshot.child("datapasien")
                    val toolsRef = snapshot.child("dataAlat")
                    val result = mutableListOf<Patient>()
                    for (patient in patientRef.children) {
                        val data = patient.value as HashMap<*, *>
                        val toolsId = data["alatId"] as String
                        val usedTool = toolsRef.child(toolsId).value as HashMap<*, *>
                        val patient = Patient(
                            FSR = (usedTool["FSR"] as String).toDouble(),
                            heartRate = (usedTool["HeartRate"] as String).toDouble(),
                            spO2 = (usedTool["SpO2"] as String).toDouble(),
                            temperature = (usedTool["Suhu"] as String).toDouble(),
                            id = data["id"] as Long,
                            patientName = data["namaPasien"] as String,
                            age = (data["umur"] as Long).toInt(),
                            createdDate = data["createdDate"] as String,
                            gender = data["gender"] as String
                        )
                        result.add(patient)
                    }
                    offer(result)
                    Log.d("DATA", "${snapshot.children}")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ERROR", error.message)
                }

            }
            ref.addValueEventListener(listener)
            awaitClose { ref.removeEventListener(listener) }
        }
    }

    suspend fun classify(patients: List<Patient>): Flow<List<Patient>> {
        return flow {
            val result = mutableListOf<Patient>()
            for (patient in patients) {
                val convertedValue = patient.convert()
                val x1 = convertedValue.age.toString()
                val x2 = if (convertedValue.gender == "L") {
                    0
                } else {
                    1
                }
                val x3 = convertedValue.temperature.toInt().toString()
                val x4 = convertedValue.FSR.toInt().toString()
                val x5 = convertedValue.spO2.toInt().toString()
                val status = service.classify(x1, x2.toString(), x3, x4, x5)
                val newPatient = patient.copy(
                    status = when (status.toInt()) {
                        4 -> PatientStatus.KRITIS
                        3 -> PatientStatus.BERAT
                        2 -> PatientStatus.SEDANG
                        1 -> PatientStatus.RINGAN
                        0 -> PatientStatus.TANPA_GEJALA
                        else -> PatientStatus.TANPA_GEJALA
                    }
                )
                result.add(newPatient)
            }
            emit(result)
        }
    }
}