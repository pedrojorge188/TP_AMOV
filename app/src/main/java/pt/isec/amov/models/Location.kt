package pt.isec.amov.models

data class Location(
    val id: String,
    var name: String,
    var latitude: Double,
    var longitude: Double,
    var description: String,
    var photoUrl: String?,
    val createdBy: String,
    val votes: Int = 0,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val grade: Double = 0.0,
    var category: String?,
    var votedBy: List<String> = emptyList()
)