package pt.isec.amov.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.viewmodels.ActionsViewModel
import pt.isec.amov.ui.viewmodels.Screens

@Composable
fun PointOfInteresetDetailsScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    pointOfInterest: PointOfInterest?
)  {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {

        if(pointOfInterest!!.votes < 2){
            Row (modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)){
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "danger",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = stringResource(id = R.string.warning_info_title), color = Color.Red)
            }

        }

        //imagem tem de ser alterada mais tarde
        Image(
            painter = painterResource(id = R.drawable.museu),
            contentDescription = "Museu",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { navHostController.navigate(Screens.POINT_OF_INTEREST_MAP.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF02458A),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(10.dp)
                    .height(52.dp)
                    .weight(1f)
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "LocationOn",
                    tint = Color.White
                )
            }
        }

        LazyColumn( modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp))
        {

            item {

                Text(
                    text = stringResource(R.string.category_txt),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "   "+ pointOfInterest!!.category.name,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.extra_information),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = pointOfInterest!!.description,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )


            }
        }
    }

}