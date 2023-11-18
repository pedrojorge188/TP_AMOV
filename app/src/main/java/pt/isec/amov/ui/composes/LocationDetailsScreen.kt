package pt.isec.amov.ui.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
import pt.isec.amov.ui.composes.items.NormalBtn
import pt.isec.amov.utils.viewmodels.Screens

@Composable
fun LocationDetailsScreen(
    navHostController: NavHostController,
    title: MutableState<String>
) {
    title.value = stringResource(id = R.string.locations_details)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
            Image(
                painter = painterResource(id = R.drawable.museu),
                contentDescription = "Museu",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            NormalBtn(
                onClick = { navHostController.navigate(Screens.LOCAL.route) }
                ,text = "Locais de interesse")

            Spacer(modifier = Modifier.height(16.dp))

        LazyColumn( modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp))
        {
            item {
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

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Avaliação Atual: 3",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomRatingBar(
                    rating = 3F,
                    onRatingChanged = {}
                )
            }
        }
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