package pt.isec.amov.models

data class PointOfInterest(
    val id: String,
    var name: String,
    val locationId: String,
    var description: String,
    var photoUrl: String?,
    var latitude: Double,
    var longitude: Double,
    var votes: Int = 0,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val grade: Double = 0.0,
    val createdBy: String,
    var category: String?,
    var report: Int = 0,
    var comment: Map<String,String> = emptyMap(),
    var avaliations: Map<String,Int> = emptyMap(),
    var reportedBy: List<String> = emptyList(),
    var votedBy: List<String> = emptyList()
){
    fun calculateAverageRating(): Double {
        if (avaliations.isEmpty()) {
            return 0.0
        }

        val totalRating = avaliations.values.sum()
        val numberOfRatings = avaliations.size
        return totalRating.toDouble() / numberOfRatings
    }
}