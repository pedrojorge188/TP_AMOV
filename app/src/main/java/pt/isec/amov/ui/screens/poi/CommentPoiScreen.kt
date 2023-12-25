package pt.isec.amov.ui.screens.poi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import pt.isec.amov.R
import pt.isec.amov.models.PointOfInterest
import pt.isec.amov.ui.viewmodels.ActionsViewModel

@Composable
fun CommentPoiScreen(
    navController: NavHostController,
    vm: ActionsViewModel,
    pointOfInterest1: LiveData<List<PointOfInterest>>,
    pointOfInterest: PointOfInterest
) {
    var comment by remember { mutableStateOf("") }
    val locals: State<List<PointOfInterest>?> = pointOfInterest1.observeAsState()
    var loocker by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (vm.error.value != null) {
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
                Text(text = "Error: ${vm.error.value}", color = Color.Red)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(vm.user.value!!.email in pointOfInterest.comment || loocker){
                Column {
                    Text(
                        text = stringResource(R.string.you_already_wrote_once),
                        modifier = Modifier.padding(8.dp),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = comment,
                        onValueChange = {
                            if (it.length <= 200) {
                                comment = it
                            }
                        },
                        label = { Text(stringResource(R.string.comments)) },
                        singleLine = false,
                        enabled = false
                    )
                }
            }else{
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = comment,
                        onValueChange = {
                            if (it.length <= 200) {
                                comment = it
                            }
                        },
                        label = { Text(stringResource(R.string.comments)) },
                        singleLine = false
                    )
                    Text(
                        text = stringResource(R.string.remaining_characters,200-comment.length),
                        modifier = Modifier.padding(8.dp),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                IconButton(
                    onClick = {
                        if(comment.isNotBlank()) {
                            vm.addComment(pointOfInterest.name, pointOfInterest.locationId, comment)
                            loocker = true
                            comment = ""
                        }
                        else
                            vm.error.value = "You Must enter all requirements!"
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        tint = Color(0xFF02458A), // Cor do ícone
                        contentDescription = "Adicionar"
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            items(locals.value!!, key = { it.id }) { item ->
                if (item.id == pointOfInterest.id) {
                    if(item.comment.isEmpty()){
                        Text(
                            text = stringResource(R.string.any_comment_yet),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item.comment.forEach { (key, value) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Row{
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        tint = Color.Gray,
                                        contentDescription = "Adicionar"
                                    )
                                    Text(
                                        text = "$key",
                                        color = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "$value",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}