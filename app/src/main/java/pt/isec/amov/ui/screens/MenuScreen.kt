package pt.isec.amov.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.viewmodels.Screens

@Composable
fun Menu(
    title: String,
    navHostController: NavHostController?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(90.dp))
            NormalBtn(
                onClick = { navHostController?.navigate(Screens.LOGIN.route) },
                text = stringResource(R.string.login)
            )
            Spacer(modifier = Modifier.height(16.dp))
            NormalBtn(
                onClick = { navHostController?.navigate(Screens.REGISTER.route) },
                text = stringResource(R.string.Register)
            )
            Spacer(modifier = Modifier.height(16.dp))
            NormalBtn(
                onClick = { navHostController?.navigate(Screens.CREDITS.route) },
                text = stringResource(R.string.Credits)
            )
        }
    }

}