package pt.isec.amov.ui.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.isec.amov.R

@Composable
fun LocationDetailsScreen(NavHostController: NavHostController, title: MutableState<String>) {
    title.value = stringResource(id = R.string.locations_details)
    Column( modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Detalhes:",
            color = Color.Black,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            fontSize = 40.sp
        )
        Image(
            painter = painterResource(id = R.drawable.museu),
            contentDescription = "Museu",
            contentScale = ContentScale.Fit,
        )

        Row {
            Column {
                Text(
                    text = "Categoria:",
                    color = Color.Black,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "    Museu",
                    color = Color.Black,
                    fontSize = 25.sp
                )
            }

        }
        Row {
            Column {
                Text(
                    text = "Informação adicional:",
                    color = Color.Black,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = " Explore a riqueza da arte e da história no conforto da sua casa. " +
                            "Descubra exposições fascinantes e mergulhe em culturas diversas.",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

        }


    }
}