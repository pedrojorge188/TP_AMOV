package pt.isec.amov.models

data class Category(
    val id: String,
    val name: String,
    val iconUrl: String?,
    val createdBy: String,
    val description: String
)
