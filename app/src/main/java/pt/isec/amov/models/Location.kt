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
    val likes: Int = 0,
    val dislikes: Int = 0,
    val grade: Double = 0.0,
    val category: String?,
    val votedBy: List<String> = emptyList()
)