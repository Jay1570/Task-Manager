package com.example.taskmanager.model.service.impl

import com.example.taskmanager.model.Task
import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.StorageService
import com.example.taskmanager.model.service.trace
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {
    override val tasks: Flow<List<Task>>
        get() = emptyFlow()

    override suspend fun getTask(taskId: String): Task? =
        firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject()

    override suspend fun save(task: Task): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId = task.copy(userId = auth.currentUserId)
            firestore.collection(TASK_COLLECTION).add(taskWithUserId).await().id
        }

    override suspend fun update(task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
        }

    override suspend fun delete(taskId: String) {
    firestore.collection(TASK_COLLECTION).document(taskId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val TASK_COLLECTION = "tasks"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}