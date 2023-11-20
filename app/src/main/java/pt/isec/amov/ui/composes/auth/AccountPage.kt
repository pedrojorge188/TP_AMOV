package pt.isec.amov.ui.composes.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.composes.items.NormalBtn

@Composable
fun AccountPage(
    navHostController: NavHostController?,
    title: MutableState<String>,
    onChangeUsername: (String) -> Unit,
    onChangePassword: (String) -> Unit
) {
    title.value = stringResource(R.string.change_data_acc)
    var updatedUsername by remember { mutableStateOf("") }
    var updatedPassword by remember { mutableStateOf("") }
    var updatedConfirmPassword by remember { mutableStateOf("") }

    val passwordsMatch by remember {
        derivedStateOf {
            updatedPassword == updatedConfirmPassword
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!passwordsMatch) {
                Text(
                    text = stringResource(R.string.as_senhas_n_o_coincidem),
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            OutlinedTextField(
                value = updatedUsername,
                onValueChange = {
                    updatedUsername = it
                },
                label = { Text(stringResource(R.string.new_username)) },
                singleLine = true
            )
            OutlinedTextField(
                value = updatedPassword,
                onValueChange = {
                    updatedPassword = it
                },
                label = { Text(stringResource(R.string.new_password)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = updatedConfirmPassword,
                onValueChange = {
                    updatedConfirmPassword = it
                },
                label = { Text(stringResource(R.string.confirmar_new_password)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NormalBtn(
                    onClick = {
                        if (passwordsMatch) {
                            onChangeUsername(updatedUsername)
                            onChangePassword(updatedPassword)
                            navHostController?.popBackStack()
                        }
                    },
                    stringResource(id = R.string.confirm)
                )
            }
        }
    }
}