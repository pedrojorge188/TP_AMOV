package pt.isec.amov.utils.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CurrentLocation {
    companion object{
        @SuppressLint("MissingPermission")
        fun getCurrentLocation(context: Context, onLocationResult: (String, String) -> Unit) {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { locationResult ->
                    val latitude = "${locationResult?.latitude ?: "0"}"
                    val longitude = "${locationResult?.longitude ?: "0"}"
                    onLocationResult(latitude,longitude)
                }
        }
    }
}