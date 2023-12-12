package pt.isec.amov.data

import android.util.Log
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.utils.firebase.StoreUtil
import java.util.UUID

class AppData {

        private val categories = mutableListOf<Category>()
        private val locations = mutableListOf<Location>()
        private val pointsOfInterest = mutableListOf<PointOfInterest>()

        val allLocations: List<Location>
            get() = locations
        val allPointsOfInterest: List<PointOfInterest>
            get() = pointsOfInterest

        fun addLocation(name: String, lat: Double, lon: Double, description: String, photoUrl: String?, createdBy: String, category: Category) {
            Log.wtf("FIREBASE","aqui")
            val newLocation = Location(
                id = UUID.randomUUID().toString(),
                name = name,
                latitude = lat,
                longitude = lon,
                description = description,
                photoUrl = photoUrl,
                createdBy = createdBy,
                category = category
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
                    category = category
                )

                StoreUtil.addPointOfInterestToLocation(newPointOfInterest)

            }
        }


    }
