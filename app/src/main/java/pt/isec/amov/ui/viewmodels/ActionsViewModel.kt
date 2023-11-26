package pt.isec.amov.ui.viewmodels

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
    var stageId: String = "";
    fun getPointOfInterestById(pointOfInterestId: String): PointOfInterest? {
        return appData.allPointsOfInterest.find { it.id == pointOfInterestId }
    }

    fun getLocationById(locationId: String): Location {
        return appData.allLocations.find { it.id == locationId }!!
    }

    fun getCategorys(): List<Category>{
        return appData.allCategories
    }
}


