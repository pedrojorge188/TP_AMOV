package pt.isec.amov.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import pt.isec.amov.R
import pt.isec.amov.ui.screens.MainScreen
import pt.isec.amov.ui.theme.PraticalWorkTheme
import pt.isec.amov.ui.viewmodels.LocationViewModel

class MainActivity : ComponentActivity() {

    private val viewModel : LocationViewModel by viewModels()
    override fun onResume() {
        super.onResume()
        viewModel.startLocationUpdates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PraticalWorkTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen();
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        )
            verifyMultiplePermissions.launch(arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED) {
            verifyMultiplePermissions.launch(arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ))
        }

        verifyPermissions()
    }


    private fun verifyPermissions() : Boolean{


        viewModel.coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        viewModel.fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            viewModel.backgroundLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else
            viewModel.backgroundLocationPermission = viewModel.coarseLocationPermission || viewModel.fineLocationPermission

        if (!viewModel.coarseLocationPermission && !viewModel.fineLocationPermission) {
            basicPermissionsAuthorization.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return false
        } else
            verifyBackgroundPermission()
        return true
    }

    private val basicPermissionsAuthorization = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        viewModel.coarseLocationPermission = results[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        viewModel.fineLocationPermission = results[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        viewModel.startLocationUpdates()
        verifyBackgroundPermission()
    }

    private fun verifyBackgroundPermission() {
        if (!(viewModel.coarseLocationPermission || viewModel.fineLocationPermission))
            return

        if (!viewModel.backgroundLocationPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            ) {
                val dlg = AlertDialog.Builder(this)
                    .setTitle("Background Location")
                    .setMessage(
                        getString(R.string.this_application_needs_your_permission_to_use_location_while_in_the_background) +
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                                    " (\"${packageManager.backgroundPermissionOptionLabel}\")."
                                else
                                    "."
                    )
                    .setPositiveButton("Ok") { _, _ ->
                        backgroundPermissionAuthorization.launch(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    }
                    .create()
                dlg.show()
            }
        }
    }

    private val backgroundPermissionAuthorization = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        viewModel.backgroundLocationPermission = result
        Toast.makeText(this,"Background location enabled: $result", Toast.LENGTH_LONG).show()
    }

    val verifyMultiplePermissions=registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
    }
    val verifyOnePermission=registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){

    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

