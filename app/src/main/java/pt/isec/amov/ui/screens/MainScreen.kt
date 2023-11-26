package pt.isec.amov.ui.screens

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pt.isec.amov.App
import pt.isec.amov.R
import pt.isec.amov.ui.Greeting
import pt.isec.amov.ui.screens.auth.AccountPage
import pt.isec.amov.ui.screens.auth.LoginScreen
import pt.isec.amov.ui.screens.auth.RegisterScreen
import pt.isec.amov.ui.screens.lists.LocationListScreen
import pt.isec.amov.ui.screens.lists.PointOfInterestListScreen
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.ActionsViewModelFactory
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val app = context.applicationContext as App
    var locationId: String = ""; var pointOfInterestId: String = "";
    val currentScreen by navController.currentBackStackEntryAsState()
    var showBackArrow by remember { mutableStateOf(false) }
    var showDetailsBtn by remember { mutableStateOf(false) }
    var showAddBtn by remember { mutableStateOf(false) }
    var expandedMenu by remember  { mutableStateOf(false) }
    var expandedDetails by remember  { mutableStateOf(false) }
    var title = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var viewModel : ActionsViewModel? = null

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        showDetailsBtn = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route,
            Screens.LOCAL_DETAILS.route, Screens.LOCATION.route, Screens.LOCATION_DETAILS.route ,
            Screens.LOCAL.route, Screens.MAP.route, Screens.CONTRIBUTION.route
        )
        showAddBtn = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route, Screens.LOCAL_DETAILS.route, Screens.LOCATION.route,  Screens.LOCATION_DETAILS.route ,  Screens.LOCAL.route, Screens.MAP.route, Screens.CONTRIBUTION.route
        )
        showBackArrow = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route, Screens.LOGIN.route,
            Screens.REGISTER.route, Screens.LOCAL_DETAILS.route, Screens.LOCATION_DETAILS.route, Screens.LOCAL.route, Screens.MAP.route, Screens.CONTRIBUTION.route
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            if (currentScreen != null && Screens.valueOf(currentScreen!!.destination.route!!).showAppBar) {
                TopAppBar(
                    title = {
                            Text(text = title.value ,fontSize = 15.sp )
                    },
                    navigationIcon = {
                        if(showBackArrow){
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.Back)
                                )
                            }
                        }
                    },
                    actions = {
                        if (showDetailsBtn) {
                            IconButton(onClick = { expandedDetails = !expandedDetails}) {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = stringResource(R.string.details)
                                )
                                DropdownMenu(
                                    expanded = expandedDetails,
                                    onDismissRequest = { expandedDetails = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.account)) },
                                        onClick = {
                                            navController.navigate(Screens.ACCOUNT_CHANGE_DATA.route)
                                            expandedDetails = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.logout)) },
                                        onClick = {
                                            navController.navigate(Screens.MENU.route);
                                            scope.launch { snackbarHostState.showSnackbar("Terminou a sessão!") }
                                            expandedDetails = false
                                        }
                                    )
                                }
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
                                    if(currentScreen!!.destination.route == Screens.LOCAL.route){
                                        DropdownMenuItem(
                                            text = { Text(stringResource(R.string.add_interest_location)) },
                                            onClick = { expandedMenu = false  }
                                        )
                                    }
                                }
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
                title.value = stringResource(id = R.string.login)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))
                LoginScreen(navHostController = navController, viewModel!!)
            }
            composable(Screens.REGISTER.route) {
                title.value = stringResource(id = R.string.Register)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))
                RegisterScreen(navHostController = navController, viewModel!!)
            }
            composable(Screens.LOCATION_DETAILS.route) {
                title.value = stringResource(id = R.string.locations_details)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))

                LocationDetailsScreen(navHostController = navController, viewModel!!, viewModel!!.getLocationById(locationId))
            }

            composable(Screens.LOCAL_DETAILS.route) {
                title.value = stringResource(R.string.detalhes_do_local_de_interesse)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))

                PointOfInteresetDetailsScreen(navHostController = navController, viewModel!!, viewModel!!.getPointOfInterestById(pointOfInterestId))
            }

            composable(Screens.LOCATION.route) {

                title.value = stringResource(id = R.string.location_list)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))

                LocationListScreen(NavHostController = navController, viewModel!!, app.appData.allLocations) {
                    locationId = it.itemId
                    navController.navigate(it.nextPage.route)
                }
            }

            composable(Screens.LOCAL.route) {
                title.value = stringResource(id = R.string.interests_locations) + viewModel!!.getLocationById(locationId).name + ")"
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))

                PointOfInterestListScreen(NavHostController = navController, viewModel!!, viewModel!!.getLocationById(locationId).pointsOfInterest) {
                    pointOfInterestId = it.itemId
                    navController.navigate(it.nextPage.route)
                }

            }

            composable(Screens.ACCOUNT_CHANGE_DATA.route) {
                title.value = stringResource(id = R.string.change_data_acc)
                viewModel = viewModel(factory = ActionsViewModelFactory(app.appData))

                AccountPage(
                    navController,
                    viewModel!!,
                    onChangeUsername = {
                            newUsername ->

                    },
                    onChangePassword = {
                            newPassword ->
                    }
                )
            }

            composable(Screens.MAP.route) {
                Greeting(Screens.MAP.route)
            }
            composable(Screens.CONTRIBUTION.route) {
                Greeting(Screens.CONTRIBUTION.route)
            }
        }
    }
}
