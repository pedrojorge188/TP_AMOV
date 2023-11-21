package pt.isec.amov.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isec.amov.data.AppData
@Suppress("UNCHECKED_CAST")
class ActionsViewModelFactory(private val appData: AppData) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActionsViewModel(appData) as T
    }
}

class ActionsViewModel(private val appData: AppData) : ViewModel(){

}


