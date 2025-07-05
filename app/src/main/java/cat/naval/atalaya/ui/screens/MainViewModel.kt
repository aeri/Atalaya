package cat.naval.atalaya.ui.screens

import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mroczis.netmonster.core.factory.NetMonsterFactory
import cz.mroczis.netmonster.core.model.cell.ICell
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _texts = MutableStateFlow(List(10) { "Texto inicial $it" })
    val texts: StateFlow<List<String>> = _texts




    init {
        fetchUpdatedTexts()
    }

    private fun fetchUpdatedTexts() {
        viewModelScope.launch {
            delay(3000) // Simulamos una llamada de red
            val updatedTexts = simulateApiCall()
            _texts.value = updatedTexts
        }
    }

    @SuppressLint("MissingPermission")
    private fun simulateApiCall(): List<String> {



        return List(10) { "Texto actualizado ${it + 1}" }
    }
}
