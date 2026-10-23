package fr.amonteiro.a10_ipi_g1_android.presentation

import androidx.lifecycle.ViewModel
import fr.amonteiro.a10_ipi_g1_android.data.remote.WeatherApiDataSource
import fr.amonteiro.a10_ipi_g1_android.domain.Weather
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")
    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")

    //Pour que le programme s'arrête, inutile sur Android
    WeatherApiDataSource.close()
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<Weather>())

    suspend fun loadWeathers(cityName: String) {

        dataList.value = WeatherApiDataSource.loadWeathers(cityName)
    }
}