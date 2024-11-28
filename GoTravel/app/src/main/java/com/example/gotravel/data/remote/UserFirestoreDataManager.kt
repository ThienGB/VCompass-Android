package com.example.gotravel.data.remote

import android.util.Log
import com.example.gotravel.data.local.dao.UserDao
import com.example.gotravel.data.model.User
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.helper.FirestoreHelper.CL_USER
import com.example.gotravel.helper.FirestoreHelper.FL_EMAIL
import com.example.gotravel.helper.FirestoreHelper.FL_FULLNAME
import com.example.gotravel.helper.FirestoreHelper.FL_PHONENUMBER
import com.example.gotravel.helper.FirestoreHelper.FL_ROLE
import com.example.gotravel.helper.FirestoreHelper.FL_STATUS
import com.example.gotravel.helper.FirestoreHelper.FL_USERID
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserFirestoreDataManager {
    private val db = Firebase.firestore
    private val userDao = UserDao()
    fun addUser(userAccount :UserAccount) {
        val user: MutableMap<String, Any> = HashMap()
        user[FL_USERID] = userAccount.userId
        user[FL_ROLE] = userAccount.role
        user[FL_FULLNAME] = userAccount.fullName
        user[FL_PHONENUMBER] = userAccount.phone
        user[FL_EMAIL] = userAccount.email
        user[FL_STATUS] = userAccount.status

        db.collection(CL_USER).document(userAccount.userId).set(user)
    }

    suspend fun getUserById(userId: String): UserAccount? {
        return try {
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
    suspend fun updateStatus(userId: String, status: String): Boolean {
        return try {
            val userRef = db.collection(CL_USER).document(userId)

            userRef.update(FL_STATUS, status).await()

            Log.d("Firestore", "User role updated successfully to: $status")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error updating user role", e)
            false
        }
    }

    suspend fun getUserByEmail(email: String?): UserAccount? {
        return try {
            // Tìm kiếm tài liệu người dùng với email khớp
            val querySnapshot = db.collection(CL_USER)
                .whereEqualTo("email", email)  // Tìm kiếm theo email trong cơ sở dữ liệu
                .get()
                .await()

            // Kiểm tra xem có tài liệu nào khớp không
            if (!querySnapshot.isEmpty) {
                // Giả sử chỉ có 1 kết quả, nếu có nhiều kết quả, bạn sẽ cần xử lý phù hợp
                val user = querySnapshot.documents[0].toObject(UserAccount::class.java)
                Log.e("User", "User found: ${user?.email}")
                user
            } else {
                Log.e("Firestore", "No user found with the email: $email")
                null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error getting user by email", e)
            null
        }
    }
    suspend fun fetchUser(onComplete: () -> Unit) {
        try {
            val result = db.collection(CL_USER).get().await()
            val users = mutableListOf<User>()
            for (document in result) {
                val user = document.toObject<User>()
                users.add(user)
            }
            if (users.isNotEmpty()) {
                userDao.insertOrUpdateUser(users, onComplete)
            } else {
                onComplete()
            }
        } catch (exception: Exception) {
            Log.w("FirestoreDataManager", "Error getting documents.", exception)
        }
    }

}