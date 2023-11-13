package pt.isec.amov.composes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pt.isec.amov.Greeting
import pt.isec.amov.R
import pt.isec.amov.composes.auth.LoginScreen
import pt.isec.amov.composes.auth.RegisterScreen

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    val currentScreen by navController.currentBackStackEntryAsState()
    var showDetailsBtn by remember { mutableStateOf(false) }
    var showAddBtn by remember { mutableStateOf(false) }
    var expandedMenu by remember  { mutableStateOf(false) }

    // Snackbar initialization
    val snackbarHostState = remember { SnackbarHostState() }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        showDetailsBtn = destination.route in arrayOf(
            Screens.DETAILS.route, Screens.LOCATION.route,  Screens.LOCAL.route, Screens.MAP.route, Screens.CONTRIBUTION.route
        )
        showAddBtn = destination.route in arrayOf(
            Screens.DETAILS.route, Screens.LOCATION.route,  Screens.LOCAL.route, Screens.MAP.route, Screens.CONTRIBUTION.route
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            if (currentScreen != null && Screens.valueOf(currentScreen!!.destination.route!!).showAppBar) {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.Back)
                            )
                        }
                    },
                    actions = {
                        if (showDetailsBtn) {
                            IconButton(onClick = { /* dropdown */ }) {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = stringResource(R.string.details)
                                )
                            }
                        }
                        if(showAddBtn){
                            IconButton(onClick = {
                                expandedMenu = !expandedMenu
                            }) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = stringResource(R.string.add)
                                )
                            }
                            DropdownMenu(
                                expanded = expandedMenu,
                                onDismissRequest = { expandedMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.add_category)) },
                                    onClick = { expandedMenu = false }
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.add_location)) },
                                    onClick = {  expandedMenu = false}
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.add_interest_location)) },
                                    onClick = { expandedMenu = false  }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF02458A),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.MENU.route,
            modifier = Modifier
                .padding(it)
        ) {
            composable(Screens.MENU.route) {
                Menu(stringResource(R.string.AppName), navController)
            }
            composable(Screens.LOGIN.route) {
                LoginScreen(navHostController = navController)
            }
            composable(Screens.REGISTER.route) {
                RegisterScreen(navHostController = navController)
            }
            composable(Screens.LOCAL.route) {
                Greeting(Screens.LOCAL.route)
            }
            composable(Screens.LOCATION.route) {
                LocationListScreen(NavHostController = navController);
            }
            composable(Screens.MAP.route) {
                Greeting(Screens.MAP.route)
            }
            composable(Screens.CONTRIBUTION.route) {
                Greeting(Screens.CONTRIBUTION.route)
            }
            composable(Screens.DETAILS.route) {
                Greeting(Screens.DETAILS.route)
            }
        }
    }
}

