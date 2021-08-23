package com.covid.covidapps.datasource

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val preference: PreferenceDataSource
) {

    @ExperimentalCoroutinesApi
    suspend fun login(email: String, password: String): Flow<User?> {
        return callbackFlow {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser!!.toUser()
                        offer(user)
                        preference.saveUserInfo(user = user)
                    }else{
                        offer(null)
                    }
                }
            awaitClose { firebaseAuth.removeAuthStateListener {} }
        }
    }

    fun FirebaseUser.toUser() =
        User(
            userEmail = email ?: "-",
            userName = displayName ?: "-",
            userMobileNo = phoneNumber ?: "-",
            userImage = displayName ?: "-"
        )

}