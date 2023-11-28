package pt.isec.amov.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isec.amov.data.AppData
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.utils.location.LocationHandler

@Suppress("UNCHECKED_CAST")
class ActionsViewModelFactory(private val appData: AppData, private val locationHandler: LocationHandler) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActionsViewModel(appData, locationHandler) as T
    }
}

class ActionsViewModel(private val appData: AppData,  private val locationHandler: LocationHandler) : ViewModel(){
    val imagePath: MutableState<String?> = mutableStateOf(null)
    private val _currentLocation = MutableLiveData(android.location.Location(null))
    val currentLocation : LiveData<android.location.Location>
        get() = _currentLocation
    private val locationEnabled : Boolean
        get() = locationHandler.locationEnabled

    init {
        locationHandler.onLocation = {
            _currentLocation.value = it
        }
    }
    fun startLocationUpdates() {
        locationHandler.startLocationUpdates()
    }

    fun stopLocationUpdates() {
        locationHandler.stopLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    fun getPointOfInterestById(pointOfInterestId: String): PointOfInterest? {
        return appData.allPointsOfInterest.find { it.id == pointOfInterestId }
    }

    fun getLocationById(locationId: String): Location {
        return appData.allLocations.find { it.id == locationId }!!
    }

    fun getCategorys(): List<Category>{
        return appData.allCategories
    }

    fun addLocation(locationName: String, locationDescription: String, authorsName: String, selectedCategory: Category, latitude: Double, longitude: Double) {
        appData.addLocation(locationName, latitude, longitude, locationDescription, imagePath.value, authorsName, selectedCategory)
    }
}


