package pt.isec.amov.ui.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel(){

    // Permissions
    var coarseLocationPermission = false
    var fineLocationPermission = false
    var backgroundLocationPermission = false

    private val _currentLocation = MutableLiveData(Location(null))
    val currentLocation : LiveData<Location>
        get() = _currentLocation

    /*
    private val locationEnabled : Boolean
        get() = <LocationHandler>.locationEnabled

    init {
        <LocationHandler>.onLocation = {
            _currentLocation.value = it
        }
    }*/

    fun startLocationUpdates() {
        if (fineLocationPermission && coarseLocationPermission) {
            //<LocationHandler>.startLocationUpdates
        }

    }

    fun stopLocationUpdates() {
        //<LocationHandler>.stopLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

}
