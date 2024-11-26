package com.example.gotravel.ui.module.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gotravel.data.local.dao.UserAccountDao
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.data.remote.User_FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val realmHelper: RealmHelper,
    private  val sharedPreferences: SharedPreferences
) : ViewModel()
{
    private val firestoreDataManager = User_FirestoreDataManager()

    private var userDao: UserAccountDao = UserAccountDao()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    private var roleUser: String? = ""
    val authState: LiveData<AuthState> = _authState

    private val _userAccount = MutableStateFlow(UserAccount())
    val userAccount: StateFlow<UserAccount> get()= _userAccount

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if(auth.currentUser==null)
        {
            _authState.value = AuthState.Unauthenticated
        }
        else
        {
            getUserFromDb()
        }
    }

    //Dang nhap bang email
    fun login(email: String, password: String)
    {
        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task
            -> if(task.isSuccessful){
                _authState.value = AuthState.Authenticated
                getUserFromDb()
            }
            else
            {
                _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
            }
        }
    }
    //                    Log.e(auth.uid,"Khong ton tai");
    //                    checkIfUserExistsAndCreateIfNot()
    //                    getUserFromDb()

    //Dang nhap bằng google
        fun signInWithGoogle(idToken: String) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        viewModelScope.launch {
                            currentUser?.linkWithCredential(credential)
                                ?.addOnCompleteListener { linkTask ->
                                    if (linkTask.isSuccessful) {
                                        Log.d("Auth", "Google account linked to existing email: ${currentUser.email}")
                                    } else {
                                        _authState.value = AuthState.Error("Failed to link Google account: ${linkTask.exception?.message}")
                                    }
                                }
                        }
                        checkIfUserExistsAndCreateIfNot();
                        getUserFromDb()

                    } else {
                        _authState.value = AuthState.Error(task.exception?.message ?: "Google sign-in failed")
                    }
                }
        }

    //Dang ky
    fun signup(email: String, password: String, fullName: String, phone: String)
    {
        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task
            -> if(task.isSuccessful){
            _authState.value = AuthState.Authenticated

            val userAccount = UserAccount()
            userAccount.userId = auth.currentUser?.uid
            userAccount.fullName = fullName
            userAccount.phone = phone
            userAccount.role = ""
            userAccount.email= email

            firestoreDataManager.addUser(userAccount);
        }else
        {
            _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
        }}
    }

    //Gui lai password
    fun sendPasswordResetLink(email: String, onComplete: (Boolean, String) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "A password reset link has been sent to your email.")
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "The email address is not registered."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email address format."
                        else -> "Failed to send reset email. Please try again."
                    }
                    onComplete(false, errorMessage)
                }
            }
    }

    //Dat role cho user
    fun setRole(role: String) {
        // Lưu vai trò vào SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("role", role)
        editor.apply()

        // Cập nhật vai trò trong cơ sở dữ liệu Firestore
        val userId = auth.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                firestoreDataManager.updateRole(userId, role)
                Log.d("AuthViewModel", "User role updated to: $role")
            }
        } else {
            Log.e("AuthViewModel", "No current user found")
        }
    }

    //Kiem tra User có ton tai trong database ko
    private fun checkIfUserExistsAndCreateIfNot() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            val userId = auth.currentUser?.uid

            // Kiểm tra xem người dùng đã có tài khoản trong Firestore chưa
            val userFromDb: UserAccount? = firestoreDataManager.getUserById(userId!!)

            if (userFromDb == null) {
                // Nếu chưa có tài khoản, tạo tài khoản mới
                val userAccount = UserAccount()
                userAccount.userId = userId
                userAccount.fullName = ""
                userAccount.role = ""

                firestoreDataManager.addUser(userAccount);
                Log.d("Auth", "New user created: ${currentUser?.displayName}")
            } else {
                Log.d("Auth", "User already exists: ${userFromDb.fullName}")
            }

        }
    }

    //Lay user
    fun getUserFromDb(){
        viewModelScope.launch {
            val userFromDb: UserAccount? = try {
                firestoreDataManager.getUserById(auth.currentUser!!.uid)
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error fetching user: ${e.message}")
                null
            }
            Log.e(userFromDb?.email, "Lay du lieu that bai")
            val editor = sharedPreferences.edit()
            editor.putString("userId", auth.currentUser?.uid)
            editor.putString("fullName", userFromDb?.fullName)
            editor.putString("role", userFromDb?.role)
            editor.putString("email", userFromDb?.email)
            editor.putString("phone", userFromDb?.phone)
            editor.apply()
            roleUser = userFromDb?.role;
            _authState.value = AuthState.Authenticated
        }
    }

    fun checkRoleNavigate()  : String?
    {
        getUserFromDb();
        return roleUser;
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun changePassword(currentPassword: String, newPassword: String, onComplete: (Boolean, String) -> Unit) {
        val user = auth.currentUser
        if (user == null) {
            onComplete(false, "User is not authenticated.")
            return
        }
        if (currentPassword == newPassword) {
            onComplete(false, "New password cannot be the same as the current password.")
            return
        }
        val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
        user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
            if (reAuthTask.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        onComplete(true, "Password changed successfully.")
                    } else {
                        onComplete(false, "Failed to update password: ${updateTask.exception?.message}")
                    }
                }
            } else {
                onComplete(false, "Current password is incorrect.")
            }
        }
    }

}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated: AuthState()
    object UnRole: AuthState()
    object AcceptRole : AuthState()
    object Loading: AuthState()
    data class Error(val message : String) : AuthState()
}