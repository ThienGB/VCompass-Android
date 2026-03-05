package com.vcompass.data.repository.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.vcompass.domain.repository.location.GeocodingDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.invoke

class AndroidGeocodingDataSource(
    private val context: Context
) : GeocodingDataSource {

    override suspend fun getShortAddress(
        latitude: Double,
        longitude: Double
    ): String? {
        return locationToAddress(
            context, latitude, longitude, AddressFormat.SHORT
        )
    }

    override suspend fun getFullAddress(
        latitude: Double,
        longitude: Double
    ): String? {
        return locationToAddress(
            context, latitude, longitude, AddressFormat.FULL
        )
    }

    override suspend fun getCity(
        latitude: Double,
        longitude: Double
    ): String? {
        return locationToAddress(
            context, latitude, longitude, AddressFormat.CITY_ONLY
        )
    }

    override suspend fun getCurrentLocation(): Location? =
        suspendCancellableCoroutine { cont ->

            val client = LocationServices
                .getFusedLocationProviderClient(context)

            client.lastLocation
                .addOnSuccessListener { location ->
                    cont.resume(location) { cause, _, _ -> null?.let { it(cause) } }
                }
                .addOnFailureListener {
                    cont.resume(null) { cause, _, _ -> null?.let { it1 -> it1(cause) } }
                }
        }
}

@Suppress("DEPRECATION")
fun locationToAddress(
    context: Context,
    latitude: Double,
    longitude: Double,
    format: AddressFormat
): String? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        val address = addresses?.firstOrNull() ?: return null

        when (format) {
            AddressFormat.SHORT -> {
                listOfNotNull(
                    address.subThoroughfare,   // số nhà
                    address.thoroughfare,      // tên đường
                    address.subLocality,       // phường/xã
                    address.locality,           // quận/huyện
                    address.subAdminArea,          // thành phố
                ).joinToString(", ")
            }

            AddressFormat.FULL -> {
                (0..address.maxAddressLineIndex)
                    .mapNotNull { address.getAddressLine(it) }
                    .joinToString(", ")
            }

            AddressFormat.CITY_ONLY -> {
                address.adminArea
            }
        }
    } catch (_: Exception) {
        null
    }
}

enum class AddressFormat {
    SHORT,
    FULL,
    CITY_ONLY
}
