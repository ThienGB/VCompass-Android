package com.accessed.data.remote.services.message

import com.accessed.data.model.response.BaseResponse
import com.accessed.data.model.response.SingleResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface FcmService {
    @GET("v1/users/{messengerId}/notifications/sync")
    suspend fun getSyncNotification(
        @Path("messengerId") messengerId: String
    ): SingleResponse<*>

    @DELETE("v1/users/{messengerId}/fcm-token")
    suspend fun removeFCMToken(
        @Path("messengerId") messengerId: String
    ): SingleResponse<Unit>
}