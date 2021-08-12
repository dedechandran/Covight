package com.covid.covidapps.datasource

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import javax.inject.Inject

class PatientDataSource @Inject constructor(
    private val db: FirebaseDatabase
) {

    @ExperimentalCoroutinesApi
    suspend fun getPatient(): Flow<Patient> {
        return callbackFlow {
            val ref = db.getReference("tools-1")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value as HashMap<*, *>
                    offer(
                        Patient(
                            FSR = data["FSR"] as Long,
                            heartRate = data["HeartRate"] as Long,
                            spO2 = data["SpO2"] as Long,
                            temperature = data["Suhu"] as Double
                        )
                    )
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
}