package pt.isec.amov

import android.app.Application
import pt.isec.amov.data.AppData

class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }


    val appData by lazy {
        AppData()
    }
}