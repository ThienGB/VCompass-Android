package com.vcompass.data.remote.client

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.vcompass.data.util.DataConstants
import com.google.gson.Gson
import com.vcompass.data.BuildConfig
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
    single {
        CustomHeaderInterceptor(get())
    }

    single { provideOkHttpClient(get(), get()) }

    single { provideRetrofitBuilder("https://resalon.onrender.com", get()) }
}

fun provideOkHttpClient(context: Context, customHeaderInterceptor: CustomHeaderInterceptor): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    val httpClientBuilder = OkHttpClient.Builder()
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
            httpClientBuilder.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts.first() as X509TrustManager
            )
            httpClientBuilder.hostnameVerifier { hostName, _ ->
                hostName.contains("accessed.co", true)
            }
        }
    } catch (_: Exception) {
    }
    return httpClientBuilder
        .connectTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(DataConstants.TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(customHeaderInterceptor)
        .addInterceptor(
            CustomUserAgentInterceptor(
                genderUserAgent(context)
            )
        )
        .retryOnConnectionFailure(true)
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