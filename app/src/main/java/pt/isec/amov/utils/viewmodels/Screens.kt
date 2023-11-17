package pt.isec.amov.utils.viewmodels

enum class Screens(display: String, val showAppBar: Boolean){
    MENU("Menu", false), //Menu principal
    LOGIN("Login",true), //Página de login
    REGISTER("Register", true), //Página de registo
    DETAILS("Details", true), //Página com detalhes de conta
    LOCATION("Location", true), //Página que lista todas as localizações
    LOCAL("Local", true), //Página que lista todas as localizações
    MAP("Map", true), //Página com o mapa de uma localização
    CONTRIBUTION("Contribution", true); //Página com as contribuições (adicionar local de interesse / categoria)
    val route : String
        get() = this.toString()
}