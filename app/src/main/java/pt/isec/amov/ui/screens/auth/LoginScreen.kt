package pt.isec.amov.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.composables.NormalBtn
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.Screens

@Composable
fun LoginScreen(navHostController: NavHostController?, viewModel: ActionsViewModel) {
    val email = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }

    val error = remember{viewModel.error}
    val user by remember{viewModel.user}

    LaunchedEffect(key1 = user){
        if(user!=null&&error.value==null)  navHostController?.navigate(Screens.LOCATION.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            if(viewModel.error.value != null) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Icon(
                        Icons.Filled.Warning,
                        contentDescription = "danger",
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Error: ${error.value}", color = Color.Red)
                }
            }

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            NormalBtn(
                onClick = {
                    viewModel.signInWithEmail(email.value,password.value)
                },
                text = stringResource(R.string.login)
            )
        }
    }
}

