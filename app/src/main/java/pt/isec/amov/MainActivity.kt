package pt.isec.amov

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pt.isec.amov.ui.theme.PraticalWorkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PraticalWorkTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen();
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

enum class Screens(display: String, val showAppBar: Boolean){
    MENU("Menu", true), //Menu principal
    LOGIN("Login",false), //Página de login
    REGISTER("Register", false), //Página de registo
    DETAILS("Details", true), //Página com detalhes de conta
    LIST("List", true), //Página que lista todas as localizações
    MAP("Map", true), //Página com o mapa de uma localização
    CONTRIBUTION("Contribution", true); //Página com as contribuições (adicionar local de interesse / categoria)
    val route : String
        get() = this.toString()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    val currentScreen by navController.currentBackStackEntryAsState()

    //snackbar initialization
    val scope = rememberCoroutineScope()
    val snackbarhoststate = remember { SnackbarHostState() }

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarhoststate)
        },
        topBar = {
            if(currentScreen != null && Screens.valueOf(currentScreen!!.destination.route!!).showAppBar)
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
                        IconButton(onClick = { /*Ir para página dos detalhes de conta*/ }
                        ) {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = stringResource(R.string.add)
                            )
                        }
                    },
                    colors =  topAppBarColors(
                        containerColor = Color(0xFF02458A),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
        },
        modifier = Modifier.fillMaxSize()
    ){
        NavHost(
            navController = navController,
            startDestination = Screens.MENU.route,
            modifier = Modifier
                .padding(it)

        ) {
            composable(Screens.MENU.route) {
                 Greeting(name = Screens.MENU.route)
            }
            composable(Screens.LOGIN.route) {
                Greeting(name = Screens.LOGIN.route)
            }
            composable(Screens.REGISTER.route) {
                Greeting(name = Screens.REGISTER.route)
            }
            composable(Screens.LIST.route) {
                Greeting(name = Screens.LIST.route)
            }
            composable(Screens.MAP.route) {
                Greeting(name = Screens.MAP.route)
            }
            composable(Screens.CONTRIBUTION.route){
                Greeting(Screens.CONTRIBUTION.route)
            }
            composable(Screens.DETAILS.route){
                Greeting(Screens.DETAILS.route)
            }
        }
    }

}