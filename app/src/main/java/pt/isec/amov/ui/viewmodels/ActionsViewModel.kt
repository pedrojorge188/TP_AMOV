package pt.isec.amov.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isec.amov.data.AppData
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest

@Suppress("UNCHECKED_CAST")
class ActionsViewModelFactory(private val appData: AppData) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActionsViewModel(appData) as T
    }
}

class ActionsViewModel(private val appData: AppData) : ViewModel(){
    val imagePath: MutableState<String?> = mutableStateOf(null)

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


