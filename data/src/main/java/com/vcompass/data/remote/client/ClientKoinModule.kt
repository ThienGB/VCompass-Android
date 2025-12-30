package com.vcompass.data.remote.client

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.vcompass.data.util.DataConstants
import com.google.gson.Gson
import com.vcompass.data.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val clientModule = module {
    single { CustomHeaderInterceptor(get()) }

    single { provideOkHttpClient(get(), get()) }

    single { provideRetrofitBuilder(BuildConfig.BASE_URL, get()) }
}

fun provideOkHttpClient(
    context: Context,
    customHeaderInterceptor: CustomHeaderInterceptor
): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?, authType: String?
            ) = Unit

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?, authType: String?
            ) = Unit

            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        }
    )

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, SecureRandom())

    val trustManager = trustAllCerts[0] as X509TrustManager

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .hostnameVerifier { _, _ -> true } // ⚠️ chỉ test
        .connectTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(customHeaderInterceptor)
        .addInterceptor(CustomUserAgentInterceptor(genderUserAgent(context)))
        .retryOnConnectionFailure(true)
        // Render fix
        .connectionPool(ConnectionPool(0, 1, TimeUnit.SECONDS))
        .build()
}


fun provideRetrofitBuilder(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
}

inline fun <reified T> provideService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

fun getAppVersionName(context: Context): String {
    return try {
        val pInfo = context.packageManager.getPackageInfo(
            context.packageName,
            0
        )
        pInfo.versionName ?: "unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "unknown"
    }
}

private fun genderUserAgent(context: Context): String {
    //format : {app_name}/{version_name} ({os_version}; {device_name}) {platform}
    val appName =
        context.packageManager?.getApplicationLabel(context.applicationInfo).toString() + " User"
    val versionName = getAppVersionName(context)
    val osVersion = Build.VERSION.RELEASE
    val deviceModel = Build.MANUFACTURER.uppercase().plus(" ${Build.MODEL}")
    val platform = "Mobile"
    return String.format(
        "%s/%s (Android %s; %s) %s",
        appName,
        versionName,
        osVersion,
        deviceModel,
        platform
    )
}
