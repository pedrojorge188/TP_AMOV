package pt.isec.amov.ui.composes

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            fontWeight = FontWeight.Bold,
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
                    fontWeight = FontWeight.Bold,
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
                    fontWeight = FontWeight.Bold,
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
        // Avaliação Atual
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Avaliação Atual: 5",
            color = Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        // Componente de Avaliação Personalizado
        Spacer(modifier = Modifier.height(8.dp))
        CustomRatingBar(
            rating = 3F,
            onRatingChanged = {
            }
        )


    }
}
@Composable
fun CustomRatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..5).forEach { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index <= rating) Color(201, 180, 0, 255) else Color.Gray,
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clickable { onRatingChanged(index.toFloat()) }
            )
        }
    }
}