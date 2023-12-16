package pt.isec.amov.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.isec.amov.R

@Composable
fun CheckInternetConnectivity() {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        val connectivityFlow = createConnectivityFlow(context)

        connectivityFlow.collect { result ->
            isConnected = result
        }
    }

    if (!isConnected) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)).clickable(enabled = false) { }
        ) {
            CenteredRowWithProgress()
        }
    }
}

fun createConnectivityFlow(context: Context): Flow<Boolean> = flow {
    while (true) {
        val isConnected = checkInternetConnection(context)
        emit(isConnected)
        delay(2000)
    }
}

@SuppressLint("ObsoleteSdkInt")
fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnectedOrConnecting == true
    }
}

@Composable
fun CenteredRowWithProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
            Text(
                text = stringResource(R.string.verifique_a_sua_liga_o),
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

