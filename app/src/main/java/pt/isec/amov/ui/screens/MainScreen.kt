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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pt.isec.amov.App
import pt.isec.amov.R
import pt.isec.amov.ui.screens.auth.AccountPage
import pt.isec.amov.ui.screens.auth.LoginScreen
import pt.isec.amov.ui.screens.auth.RegisterScreen
import pt.isec.amov.ui.screens.lists.LocationListScreen
import pt.isec.amov.ui.screens.lists.PointOfInterestListScreen
import pt.isec.amov.ui.screens.maps.LocationMapScreen
import pt.isec.amov.ui.screens.maps.PointOfInterestMapScreen
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController(), viewModel : ActionsViewModel, app: App) {

    val context = LocalContext.current
    val currentScreen by navController.currentBackStackEntryAsState()
    var showBackArrow by remember { mutableStateOf(false) }
    var showDetailsBtn by remember { mutableStateOf(false) }
    var showAddBtn by remember { mutableStateOf(false) }
    var expandedMenu by remember  { mutableStateOf(false) }
    var expandedDetails by remember  { mutableStateOf(false) }
    var title = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        showDetailsBtn = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route,
            Screens.POINT_OF_INTEREST_DETAILS.route, Screens.LOCATION.route, Screens.LOCATION_DETAILS.route ,
            Screens.POINT_OF_INTEREST.route, Screens.LOCATION_MAP.route, Screens.ADD_LOCATION.route, Screens.POINT_OF_INTEREST_MAP.route
        )
        showAddBtn = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route, Screens.POINT_OF_INTEREST_DETAILS.route, Screens.LOCATION.route,  Screens.LOCATION_DETAILS.route ,  Screens.POINT_OF_INTEREST.route, Screens.LOCATION_MAP.route, Screens.POINT_OF_INTEREST_MAP.route, Screens.ADD_LOCATION.route
        )
        showBackArrow = destination.route in arrayOf(
            Screens.ACCOUNT_CHANGE_DATA.route, Screens.LOGIN.route, Screens.ADD_POI.route,
            Screens.REGISTER.route, Screens.CREDITS.route, Screens.POINT_OF_INTEREST_DETAILS.route, Screens.LOCATION_DETAILS.route, Screens.POINT_OF_INTEREST.route, Screens.LOCATION_MAP.route, Screens.ADD_LOCATION.route , Screens.POINT_OF_INTEREST_MAP.route
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
                                        onClick = {
                                            navController.navigate(Screens.ADD_LOCATION.route);
                                            expandedMenu = false
                                        }
                                    )
                                    if(currentScreen!!.destination.route == Screens.POINT_OF_INTEREST.route){
                                        DropdownMenuItem(
                                            text = { Text(stringResource(R.string.add_interest_location)) },
                                            onClick = {
                                                navController.navigate(Screens.ADD_POI.route);
                                                expandedMenu = false
                                            }
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
                LoginScreen(navHostController = navController, viewModel)
            }
            composable(Screens.REGISTER.route) {
                title.value = stringResource(id = R.string.Register)
                RegisterScreen(navHostController = navController, viewModel)
            }
            composable(Screens.CREDITS.route) {
                title.value = stringResource(id = R.string.Credits)
                CreditsScreen(navHostController = navController, viewModel)
            }
            composable(Screens.LOCATION_DETAILS.route) {
                title.value = viewModel.getLocation().name
                LocationDetailsScreen(navHostController = navController, viewModel, viewModel.getLocation())
            }

            composable(Screens.POINT_OF_INTEREST_DETAILS.route) {
                title.value = viewModel.getPointOfInterest()!!.name

                PointOfInteresetDetailsScreen(navHostController = navController,
                    viewModel, viewModel.getPointOfInterest())
            }

            composable(Screens.LOCATION.route) {

                title.value = stringResource(id = R.string.location_list)

                LocationListScreen(NavHostController = navController, viewModel, app.appData.allLocations) {
                    viewModel.locationId.value = it.itemId
                    navController.navigate(it.nextPage.route)
                }
            }

            composable(Screens.POINT_OF_INTEREST.route) {
                title.value = stringResource(id = R.string.interests_locations) + viewModel.getLocation().name + ")"

                PointOfInterestListScreen(NavHostController = navController, viewModel, viewModel.getLocation().pointsOfInterest) {
                    viewModel.pointOfInterestId.value = it.itemId
                    navController.navigate(it.nextPage.route)
                }

            }

            composable(Screens.ACCOUNT_CHANGE_DATA.route) {
                title.value = stringResource(id = R.string.change_data_acc)

                AccountPage(
                    navController,
                    viewModel,
                    onChangeUsername = {
                            newUsername ->

                    },
                    onChangePassword = {
                            newPassword ->
                    }
                )
            }
            composable(Screens.LOCATION_MAP.route) {
                title.value = viewModel.getLocation().name
                LocationMapScreen(navHostController = navController, viewModel = viewModel, location = viewModel.getLocation())
            }

            composable(Screens.POINT_OF_INTEREST_MAP.route) {
                title.value = viewModel.getLocation().name
                PointOfInterestMapScreen(navHostController = navController, viewModel = viewModel, item = viewModel.getPointOfInterest()!!)
            }

            composable(Screens.ADD_LOCATION.route) {
                title.value = stringResource(R.string.add_locations)
                AddLocationScreen(navController, viewModel);
            }
            composable(Screens.ADD_POI.route) {
                title.value = stringResource(R.string.add_interest_location)
                AddPointOfInterestScreen(navController, viewModel);
            }
        }
    }
}

