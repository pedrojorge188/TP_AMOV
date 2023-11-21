package pt.isec.amov.ui.viewmodels

enum class Screens(display: String, val showAppBar: Boolean){
    MENU("Menu", false), //Menu principal
    LOGIN("Login",true), //Página de login
    REGISTER("Register", true), //Página de registo
    LOCAL_DETAILS("LocalDetails", true), //Página com detalhes dos locais de interesse
    LOCATION("Location", true), //Página que lista todas as localizações
    LOCATION_DETAILS("LocationDetails", true), //Página com os detalhes de uma localização
    LOCAL("Local", true), //Página que lista todas as localizações
    MAP("Map", true), //Página com o mapa de uma localização
    CONTRIBUTION("Contribution", true), //Página com as contribuições (adicionar local de interesse / categoria)
    ACCOUNT_CHANGE_DATA("Contribution", true);//Página com detalhes de conta

    val route : String
        get() = this.toString()
}