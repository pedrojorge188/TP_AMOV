package pt.isec.amov.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isec.amov.data.AppData
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.models.User
import pt.isec.amov.models.toUser
import pt.isec.amov.utils.firebase.AuthUtil
import pt.isec.amov.utils.location.LocationHandler


@Suppress("UNCHECKED_CAST")
class ActionsViewModelFactory(private val appData: AppData, private val locationHandler: LocationHandler) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActionsViewModel(appData, locationHandler) as T
    }
}

class ActionsViewModel(private val appData: AppData,  private val locationHandler: LocationHandler) : ViewModel(){

    private val _user = mutableStateOf(AuthUtil.currentUser?.toUser())
    val user : MutableState<User?>
        get() = _user

    private val _error = mutableStateOf<String?>(null)
    val error: MutableState<String?>
        get() = _error

    val imagePath: MutableState<String?> = mutableStateOf(null)
    var locationId:  MutableState<String?>  = mutableStateOf("")
    var pointOfInterestId:  MutableState<String?>  = mutableStateOf("")

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

    fun getPointOfInterest(): PointOfInterest? {
        /*
        viewModelScope.launch {
            appData.loadData()
        }
        */
        return  appData.allPointsOfInterest.value?.find {
            it.id == this.pointOfInterestId.value.toString()
        }
    }

    fun getLocation(): Location {
        return appData.allLocations.value?.find { it.id == locationId.value.toString() }!!
    }

    fun getCategorys(): LiveData<List<Category>> {
        return appData.allCategory
    }

    fun getPointOfInterestList(): LiveData<List<PointOfInterest>> {
        val response = MutableLiveData<List<PointOfInterest>>()

        appData.allPointsOfInterest.observeForever { allPOIs ->
            val filteredPOIs = allPOIs.filter { it.locationId == locationId.value.toString() }
            response.value = filteredPOIs
        }
        return response
    }

    fun addLocation(locationName: String, locationDescription: String, selectedCategory: Category, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            appData.addLocation(locationName, latitude, longitude, locationDescription, "/images"+imagePath.value, _user.value!!.email, selectedCategory)
            imagePath.value = null
        }
    }

    fun addPOI(poiName: String, poiDescription: String, selectedCategory: Category, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            appData.addPointOfInterestToLocation(locationId.value.toString(),poiName,poiDescription,"/images"+imagePath.value,latitude,longitude,user.value!!.email,selectedCategory)
            imagePath.value = null
        }
    }

    /*Auth services*/
    fun createUserWithEmail(email: String, password: String){
        if(email.isBlank() || password.isBlank()){
            return
        }
        viewModelScope.launch {
            AuthUtil.createUserWithEmail(email,password) {
                    expt ->
                if(expt == null)
                    _user.value = AuthUtil.currentUser?.toUser()
                _error.value = expt?.message
            }
        }
    }

    fun signInWithEmail(email : String, password: String){
        if(email.isBlank() || password.isBlank()){
            return
        }
        viewModelScope.launch {
            AuthUtil.signInWithEmail(email,password) {
                    expt ->
                if(expt == null)
                    _user.value = AuthUtil.currentUser?.toUser()
                _error.value = expt?.message
            }
        }
    }

    fun signOut() {
        AuthUtil.signOut()
        _user.value = null
        _error.value = null
    }

    fun deleteLocation(id: String) {

    }

    fun deletePOI(name: String) {
        viewModelScope.launch {
            appData.deletePOI(name)
        }
    }

}