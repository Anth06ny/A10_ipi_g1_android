package fr.amonteiro.a10_ipi_g1_android.data.fake

import fr.amonteiro.a10_ipi_g1_android.domain.Weather

object WeatherFakeDataSource {

    fun loadFakeData() = listOf(
        Weather(
            id = 1,
            name = "Paris",
            temp = 18.5,
            description = "ciel dégagé",
            icon = "https://picsum.photos/200",
            speed = 5.0
        ),
        Weather(
            id = 2,
            name = "Toulouse",
            temp = 22.3,
            description = "partiellement nuageux",
            icon = "https://picsum.photos/201",
            speed = 3.2
        ),
        Weather(
            id = 3,
            name = "Toulon",
            temp = 25.1,
            description = "ensoleillé",
            icon = "https://picsum.photos/202",
            speed = 6.7
        ),
        Weather(
            id = 4,
            name = "Lyon",
            temp = 19.8,
            description = "pluie légère",
            icon = "https://picsum.photos/203",
            speed = 4.5
        )
    ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel

}