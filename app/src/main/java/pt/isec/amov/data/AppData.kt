package pt.isec.amov.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.utils.firebase.StoreUtil
import java.util.UUID

class AppData {

        private val categories = mutableListOf<Category>()
        private val locations = mutableListOf<Location>()
        private val pointsOfInterest = mutableListOf<PointOfInterest>()

        init {
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                loadData()
            }
        }
        fun loadData() {

            val poi = mutableListOf<PointOfInterest>()

            StoreUtil.loadCategoriesFromFireStone(
                onCategoriesLoaded = { loadedCategories ->
                    setCategories(loadedCategories)
                    Log.d("LOADED_CATEGORIES:", loadedCategories.toString());
                }
            )

            StoreUtil.readLocationsFromFirebase() {
                loadedLocations -> setLocations(loadedLocations)
                Log.d("LOADED_LOCATIONS:", loadedLocations.toString());
            }

            StoreUtil.readPOIFromFirebase() {
                loadedLocations -> setPointsOfInterest(loadedLocations)
                Log.d("LOADED_POI:", loadedLocations.toString());
            }


        }

        val allLocations: List<Location>
            get() = locations
        val allPointsOfInterest: List<PointOfInterest>
            get() = pointsOfInterest
        val allCategory: List<Category>
            get() = categories

        fun setLocations(newLocations: List<Location>) {
            locations.clear()
            locations.addAll(newLocations)
        }

        fun setPointsOfInterest(newPointsOfInterest: List<PointOfInterest>) {
            pointsOfInterest.clear()
            pointsOfInterest.addAll(newPointsOfInterest)
        }

        fun setCategories(newCategories: List<Category>) {
            categories.clear()
            categories.addAll(newCategories)
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

            val location = locations.find { it.id == locationId }

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

    }
