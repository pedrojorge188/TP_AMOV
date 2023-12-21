package pt.isec.amov.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun CategoryDetailsScreen(
    navHostController: NavHostController,
    viewModel: ActionsViewModel,
    category: Category?
) {

    LazyColumn( modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 20.dp))
    {

        item {
            val int=getResourceIdForImage(category?.iconUrl ?: "")

                Row {
                    if (int !=null) {
                        Image(

                            painter = painterResource(id = int),
                            contentDescription = null, // Pode ser ajustado conforme necessário
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)

                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.category_txt),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        Text(
                            text = "   " + (category?.name ?: "failed to load"),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }

            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.extra_information),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = "   "+ (category?.description ?: "failed to load"),                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )





        }
    }
}
private fun getResourceIdForImage(imageName: String): Int? {
    // Mapeia o nome da imagem para o ID do recurso
    return when (imageName) {
        "cidade" -> R.drawable.img_cidade
        "desporto" -> R.drawable.img_desporto
        "ginasio" -> R.drawable.img_ginasio
        "montanhas" -> R.drawable.img_montanhas
        "restaurante" -> R.drawable.img_restaurante
        "museu" -> R.drawable.img_museu
        "natureza" -> R.drawable.img_natureza
        "praia" -> R.drawable.img_praia
        else -> null
    }
}