package fr.amonteiro.a10_ipi_g1_android.data.remote

import fr.amonteiro.a10_ipi_g1_android.domain.Weather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun main() {
    val list = WeatherApiDataSource.loadWeathers("Nice")
    for (w in list) {
        println(w.getResume())
    }
}

object WeatherApiDataSource {

    private const val API_URL =
        "https://api.openweathermap.org/data/2.5/find?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr"

    //Création et réglage du client
    private val client = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }

    //Retourne la liste des points météos de la ville en paramètre
    suspend fun loadWeathers(cityName: String): List<Weather> {
        //Requête avec ktor
        val response = client.get(API_URL) {
            parameter("q", cityName)
        }

        //Récupérer les DTO
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body<WeatherAPIResultDTO>().list.map { weatherDTO ->
            val urlIcon =
                "https://openweathermap.org/img/wn/${weatherDTO.weather.firstOrNull()?.icon ?: "-"}@4x.png"

            Weather(
                id = weatherDTO.id,
                name = weatherDTO.name,
                temp = weatherDTO.main.temp,
                speed = weatherDTO.wind.speed,
                description = weatherDTO.weather.firstOrNull()?.description ?: "-",
                icon = urlIcon
            )
        }
    }

    fun close() = client.close()
}

/* -------------------------------- */
// DTO
/* -------------------------------- */
@Serializable
data class WeatherAPIResultDTO(val list: List<WeatherDTO>)

@Serializable
data class WeatherDTO(
    val id: Int,
    val name: String,
    val main: MainDTO,
    val wind: WindDTO,
    val weather: List<DescriptionDTO>
)

@Serializable
data class MainDTO(val temp: Double)

@Serializable
data class WindDTO(var speed: Double)

@Serializable
data class DescriptionDTO(val description: String, val icon: String)