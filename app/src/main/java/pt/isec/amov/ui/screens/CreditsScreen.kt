package pt.isec.amov.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun CreditsScreen(navHostController: NavHostController, viewModel: ActionsViewModel) {
    val students = listOf(
        Pair("Daniel Bravo", "2021137795"),
        Pair("Pedro Jorge", "2021142041"),
        Pair("Ricardo Tavares", "2021144652")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF7AA5D0))
            ) {
                // Adicione um logotipo ou imagem aqui se desejar
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.Center)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.background)
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Trabalho de Arquitéturas Móveis",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        items(students) { (studentName, studentNumber) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Nome: $studentName",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Nº: $studentNumber",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Curso: LEI",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
