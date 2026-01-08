package com.vcompass.data.remote.client

import com.vcompass.data.local.SecureStorageHelper
import com.vcompass.data.remote.services.auth.AuthService
import com.vcompass.domain.model.request.login.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val secureStorageHelper: SecureStorageHelper,
    private val authService: AuthService
) : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null
        val refreshToken = secureStorageHelper.refreshToken
            ?: return null
        val newAccessToken = runBlocking {
            try {
                authService.refreshToken(
                    RefreshTokenRequest(refreshToken)
                ).data?.tokens?.accessToken
            } catch (_: Exception) {
                null
            }
        } ?: return null

        secureStorageHelper.accessToken = newAccessToken

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var prior = response.priorResponse
        while (prior != null) {
            result++
            prior = prior.priorResponse
        }
        return result
    }
}
