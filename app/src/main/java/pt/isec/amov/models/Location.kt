package pt.isec.amov.models

data class Location(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val photoUrl: String?,
    val createdBy: String,
    val votes: Int = 0,
    val grade: Int = 1,
    val category: String?
)