package com.vcompass.data.remote.client

import com.vcompass.data.local.SecureStorageHelper
import com.vcompass.data.util.DataConstants
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.xml.sax.Locator
import java.util.Locale

class CustomHeaderInterceptor(val secureStorageHelper: SecureStorageHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.toString().replace("%3D", "=")
        val original = chain.request().newBuilder().url(newUrl)

        original.let { builder ->
            secureStorageHelper.accessToken?.let {
                builder.header(
                    HEADER_ACCESS_TOKEN,
                    "Bearer $it"
                )
            }

            builder.addHeader(
                HEADER_ACCEPT_LANGUAGE,
                Locale.getDefault().language
            )

            secureStorageHelper.userId?.let {
                builder.addHeader(
                    HEADER_USER_ID,
                    it
                )
            }
        }
        val request = original.build()

        return try {
            chain.proceed(request)
        } catch (ex: Exception) {
            val mess = DataConstants.NETWORK_ERROR_MESS
            return Response.Builder()
                .request(request)
                .code(DataConstants.NETWORK_ERROR_CODE)
                .protocol(Protocol.HTTP_2)
                .body(mess.toResponseBody(null))
                .message(mess).build()
        }
    }

    companion object {
        const val HEADER_ACCESS_TOKEN = "Authorization"
        const val HEADER_FCM_TOKEN = "x-fcm-token"
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
        const val HEADER_USER_ID = "UserID"
        const val HEADER_MESSENGER_ID = "MessengerID"
        const val HEADER_CALENDAR_USER_ID = "CalendarUserID"
        const val HEADER_CALENDAR_ID = "CalendarID"
    }
}