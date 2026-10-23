package fr.amonteiro.a10_ipi_g1_android.exo

import fr.amonteiro.a10_ipi_g1_android.data.remote.WindDTO


class MyLiveData<T>(value: T) {

    var value = value
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action: (T) -> Unit = {}
        set(newAction) {
            field = newAction
            newAction.invoke(value)
        }
}

fun main() {

    var toto = MyLiveData(WindDTO(5.0))
    toto.action = { println(it) }


    toto.value.speed = 10.0
    toto.value = WindDTO(10.0)


    // exo1()
}

fun exo1() {
    //Déclaration
    val hour: (Int) -> Int = { it: Int -> it / 60 }
    val hour2 = { it: Int -> it / 60 }
    val hour3: (Int) -> Int = { it -> it / 60 }
    val hour4: (Int) -> Int = { it / 60 }

    var minToMinHour: ((Int?) -> Pair<Int, Int>?)? =
        { if (it != null) Pair(it / 60, it % 60) else null }
    //val minToMinHourV2 : (Int?) -> Pair<Int,Int>? = { it?.let { Pair(it/60, it%60) }  }

    println(minToMinHour?.invoke(123))
    minToMinHour = null


    var res = hour(123)
    println(res)
}