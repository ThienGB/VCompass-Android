package com.example.gotravel.ui.module.main.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gotravel.MainApplication
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.example.gotravel.ui.module.login.AuthViewModel
import com.example.gotravel.ui.module.login.ForgetPasswordUI
import com.example.gotravel.ui.module.login.LoginUI
import com.example.gotravel.ui.module.login.RegisterUI
import com.example.gotravel.ui.module.login.SelectRoleScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class MainLoginActivity : ComponentActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var realmHelper: RealmHelper
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper

        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
        viewModel = AuthViewModel(realmHelper, sharedPreferences)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("129815532000-87hv6rracdu16grijets62jp2tu7r9g9.apps.googleusercontent.com") // Replace with your Firebase Web Client ID
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            val navController = rememberNavController()
            NavHostGraph(navController, viewModel, googleSignInClient)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    viewModel.signInWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Log.e("LoginActivity", "Google sign-in failed", e)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SIGN_IN = 9001
    }
}

@Composable
fun NavHostGraph(
    navController: NavHostController,
    viewModel: AuthViewModel,
    googleSignInClient: GoogleSignInClient
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginUI(navController, viewModel, googleSignInClient)
        }
        composable("register") {
            RegisterUI(navController, viewModel)
        }
        composable("forget-password")
        {
            ForgetPasswordUI(navController, viewModel)
        }
        composable("select-role")
        {
            SelectRoleScreen(navController, viewModel)
        }
    }
}
