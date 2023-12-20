package pt.isec.amov.models

data class PointOfInterest(
    val id: String,
    val name: String,
    val locationId: String,
    val description: String,
    val photoUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val votes: Int = 0,
    val grade: Double = 0.0,
    val createdBy: String,
    val category: String?
)