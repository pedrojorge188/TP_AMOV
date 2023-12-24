package pt.isec.amov.models

data class Category(
    val id: String,
    var name: String,
    var iconUrl: String?,
    val createdBy: String,
    var description: String
)
