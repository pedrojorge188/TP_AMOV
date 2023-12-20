package pt.isec.amov.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.utils.firebase.StoreUtil
import java.util.UUID

class AppData {

        private val _categories = MutableLiveData<List<Category>>()
        private val _locations = MutableLiveData<List<Location>>()
        val locations: LiveData<List<Location>>
            get() = _locations
        val categories: LiveData<List<Location>>
            get() = _locations

        private val _pointsOfInterest = MutableLiveData<List<PointOfInterest>>()
        val pointsOfInterest: LiveData<List<PointOfInterest>>
            get() = _pointsOfInterest

        init {
            val collectionsToObserve = listOf("category", "locations", "pointsOfInterest")
            StoreUtil.observeCollectionsForChanges(collectionsToObserve) {
                loadData()
            }
        }

        fun loadData() {

            StoreUtil.loadCategoriesFromFireStone(
                onCategoriesLoaded = { loadedCategories ->
                    setCategories(loadedCategories)
                    Log.d("LOADED_CATEGORIES:", loadedCategories.toString());
                }
            )

            StoreUtil.readPOIFromFirebase(
                onPOILoaded = { poi -> setPointsOfInterest(poi)
                    Log.d("LOADED_POI:", poi.toString());
                }
            )

            StoreUtil.readLocationsFromFirebase() {
                loadedLocations -> setLocations(loadedLocations)
                Log.d("LOADED_LOCATIONS:", loadedLocations.toString());
            }
            
        }

        val allLocations: LiveData<List<Location>>
            get() = _locations
        val allPointsOfInterest: LiveData<List<PointOfInterest>>
            get() = _pointsOfInterest
        val allCategory: LiveData<List<Category>>
            get() = _categories

        fun setLocations(newLocations: List<Location>) {
            _locations.value = newLocations
        }

        fun setPointsOfInterest(newPointsOfInterest: List<PointOfInterest>) {
              _pointsOfInterest.value = newPointsOfInterest
        }

        fun setCategories(newCategories: List<Category>) {
            _categories.value = newCategories
        }

        fun addLocation(name: String, lat: Double, lon: Double, description: String, photoUrl: String?, createdBy: String, category: Category) {

            val newLocation = Location(
                id = UUID.randomUUID().toString(),
                name = name,
                latitude = lat,
                longitude = lon,
                description = description,
                photoUrl = photoUrl,
                createdBy = createdBy,
                category = category.name
            )

            StoreUtil.addLocationToFirestore(location = newLocation )
        }

        fun addCategory(name: String, iconUrl: String?, description: String) {
            val newCategory = Category(
                id = UUID.randomUUID().toString(),
                name = name,
                iconUrl = iconUrl,
                description = description
            )

            StoreUtil.addCategory(newCategory)
        }

        fun addPointOfInterestToLocation(locationId: String, name: String, description: String, photoUrl: String?, latitude: Double, longitude: Double, createdBy: String, category: Category) {

            val location = _locations.value?.find { it.id == locationId }

            if (location != null) {
                val newPointOfInterest = PointOfInterest(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    locationId = locationId,
                    description = description,
                    photoUrl = photoUrl,
                    latitude = latitude,
                    longitude = longitude,
                    createdBy = createdBy,
                    category = category.name
                )

                StoreUtil.addPointOfInterestToLocation(locationId,newPointOfInterest)

            }
        }

    fun deletePOI(name: String) {
        StoreUtil.deletePointOfInterest(name)
    }

    fun deleteLocation(id: String) {
        StoreUtil.deleteLocation(id)
    }
    fun deleteCategory(id: String) {
        StoreUtil.deleteCategory(id)
    }

}
