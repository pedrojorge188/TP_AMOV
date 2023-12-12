package pt.isec.amov

import android.app.Application
import com.google.android.gms.location.LocationServices
import pt.isec.amov.data.AppData
import pt.isec.amov.utils.location.FusedLocationHandler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    val locationHandler by lazy {
        val locationProvider = LocationServices.getFusedLocationProviderClient(this)
        FusedLocationHandler(locationProvider)
    }

    val appData by lazy {
        AppData()
    }
}