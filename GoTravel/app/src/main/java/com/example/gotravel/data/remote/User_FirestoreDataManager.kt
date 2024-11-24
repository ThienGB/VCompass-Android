package com.example.gotravel.data.remote

import android.util.Log
import com.example.gotravel.data.model.UserAccount
import kotlinx.coroutines.*
import com.example.gotravel.helper.FirestoreHelper.CL_USER
import com.example.gotravel.helper.FirestoreHelper.FL_FULLNAME
import com.example.gotravel.helper.FirestoreHelper.FL_PHONE
import com.example.gotravel.helper.FirestoreHelper.FL_ROLE
import com.example.gotravel.helper.FirestoreHelper.FL_USERID
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class User_FirestoreDataManager {
    private val db = Firebase.firestore

    fun addUser(userAccount :UserAccount) {
        val user: MutableMap<String, Any> = HashMap()
        user[FL_USERID] = userAccount.userId.toString()
        user[FL_ROLE] = userAccount.role.toString()
        user[FL_FULLNAME] = userAccount.fullName.toString()
        user[FL_PHONE] = userAccount.phone.toString()

        db.collection(CL_USER).document(userAccount.userId.toString()).set(user)
    }

    suspend fun getUserById(userId: String): UserAccount? {
        return try {
            // Đợi task hoàn tất (chỉ nên dùng trong luồng khác UI thread)
            val document = db.collection(CL_USER).document(userId).get().await()

            if (document.exists()) {
                val user = document.toObject(UserAccount::class.java)
                Log.e(user?.email ?: "No Email", "In Db")
                user
            } else {
                Log.e("Firestore", "Document does not exist")
                null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error getting user by ID", e)
            null
        }
    }

    suspend fun updateRole(userId: String, newRole: String): Boolean {
        return try {
            val userRef = db.collection(CL_USER).document(userId)

            userRef.update(FL_ROLE, newRole).await()

            Log.d("Firestore", "User role updated successfully to: $newRole")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error updating user role", e)
            false
        }
    }

}