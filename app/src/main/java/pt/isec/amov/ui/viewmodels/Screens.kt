package pt.isec.amov.ui.viewmodels

enum class Screens(display: String, val showAppBar: Boolean){
    MENU("Menu", false),
    LOGIN("Login",true),
    REGISTER("Register", true),
    POINT_OF_INTEREST_DETAILS("LocalDetails", true),
    LOCATION("Location", true),
    LOCATION_DETAILS("LocationDetails", true),
    CATEGORY_DETAILS("CategoryDetails", true),
    POINT_OF_INTEREST("Local", true),
    LOCATION_MAP("LocationMap", true),
    POINT_OF_INTEREST_MAP("Map", true),
    ADD_LOCATION("addLocation", true),
    MANAGE_CATEGORY("ManageCategory", true),
    ADD_POI("addPOI", true),
    ACCOUNT_CHANGE_DATA("Contribution", true),
    CREDITS("Credits", true),
    MAP_OVERVIEW("allListedMap", true);
    val route : String
        get() = this.toString()
}


data class NavigationData(val itemId : String, val nextPage: Screens)
