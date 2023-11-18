package pt.isec.amov.ui.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.composes.items.NormalBtn
import pt.isec.amov.utils.viewmodels.Screens

@Composable
fun Menu(
    title: String,
    navHostController: NavHostController?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight()
                .wrapContentWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(50.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.45f)
        ) {

            NormalBtn(
                onClick = { navHostController?.navigate(Screens.LOGIN.route) },
                text = stringResource(R.string.login)
            )
            NormalBtn(
                onClick = { navHostController?.navigate(Screens.REGISTER.route) },
                text = stringResource(R.string.Register)
            )
            NormalBtn(onClick = {}, text = "Créditos")
        }
    }
}