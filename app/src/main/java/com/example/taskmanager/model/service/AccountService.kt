package com.example.taskmanager.model.service

import com.example.taskmanager.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun createAnonymousAccount()
    suspend fun authenticate(email: String, password: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()

}