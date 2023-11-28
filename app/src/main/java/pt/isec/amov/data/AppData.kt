package pt.isec.amov.data

import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import java.util.UUID

class AppData {

        private val locations = mutableListOf<Location>()
        private val pointsOfInterest = mutableListOf<PointOfInterest>()
        private val categories = mutableListOf<Category>()
        init {

            addCategory(name = "Cidade", iconUrl = "", description = "Grande cidade com muitas pessoas")
            addCategory(name = "Parque", iconUrl = "", description = "Local de interesse")
            addCategory(name = "Centro Comercial", iconUrl = "", description = "Local de interesse")

            addLocation("New York", 40.730610, -73.935242, "Descrição da Localização 1", "", "user123", categories[0])
            addLocation("Lisboa",  38.7071, -9.13549, "Descrição da Localização 2", "", "user456", categories[0])

            addPointOfInterestToLocation(locations[0].id, "Central Park", "Descrição do Ponto de Interesse 1", "url_ponto1", 40.785091, -73.968285, "user789", categories[1])
            addPointOfInterestToLocation(locations[1].id, "Colombo", "Descrição do Ponto de Interesse 2", "url_ponto2", 38.7558, -9.1885, "user012", categories[2])
            addPointOfInterestToLocation(locations[1].id, "Vasco da gama", "Descrição do Ponto de Interesse 2", "url_ponto2", 38.771496914 , -9.088166314, "user012", categories[2])
        }

        val allLocations: List<Location>
            get() = locations
        val allPointsOfInterest: List<PointOfInterest>
            get() = pointsOfInterest

        val allCategories: List<Category>
            get() = categories

        fun addLocation(name: String, lat: Double, lon: Double, description: String, photoUrl: String?, createdBy: String, category: Category) {
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

            locations.add(newLocation)
        }

        fun addCategory(name: String, iconUrl: String?, description: String) {
            val newCategory = Category(
                name = name,
                iconUrl = iconUrl,
                description = description
            )

            categories.add(newCategory)
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

                pointsOfInterest.add(newPointOfInterest)
                location.pointsOfInterest.add(newPointOfInterest)

            } else {

            }
        }


    }
