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
            addCategory(name = "Museu", iconUrl = "", description = "Local de interesse")
            addCategory(name = "Discoteca", iconUrl = "", description = "Local de interesse")

            addLocation("New York", 123.456, 789.012, "Descrição da Localização 1", "url_localizacao1", "user123", categories[0])
            addLocation("Lisboa", 234.567, 890.123, "Descrição da Localização 2", "url_localizacao2", "user456", categories[0])

            addPointOfInterestToLocation(locations[0].id, "Museu do americano", "Descrição do Ponto de Interesse 1", "url_ponto1", 123.456, 789.012, "user789", categories[1])
            addPointOfInterestToLocation(locations[1].id, "Urban", "Descrição do Ponto de Interesse 2", "url_ponto2", 234.567, 890.123, "user012", categories[2])
            addPointOfInterestToLocation(locations[1].id, "LostInRio", "Descrição do Ponto de Interesse 2", "url_ponto2", 234.567, 890.123, "user012", categories[2])
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
