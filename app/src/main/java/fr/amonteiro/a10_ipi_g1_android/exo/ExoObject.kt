package fr.amonteiro.a10_ipi_g1_android.exo

import android.R.attr.name
import java.time.temporal.TemporalAdjusters.next


class RandomName(){
    private val list = arrayListOf("Toto", "Titi", "Bob")
    private  var oldValue =""

    fun pair() = Pair(nextDiff(), nextDiff())

    fun nextDiffV3()=list.filter { it != oldValue  }.random().also { oldValue = it }

    fun nextDiffV2(): String {
        oldValue =list.filter { it != oldValue  }.random()
        return oldValue
    }

    fun nextDiff(): String {
        var newValue = next()
        while(newValue == oldValue ){
            newValue = next()
        }
        oldValue = newValue
        return oldValue
    }

    fun addAll(vararg names:String){
        for(name in names){
            add(name)
        }
    }

    fun next() = list.random()

    fun add(name : String){
        if(name.isNotBlank() && name !in list){
            list.add(name)
        }
    }




}

data class CarEntity(val marque : String, val model : String) {
    var color = ""
}

fun main() {
    var car = CarEntity("Seat", "Leon")
    car.color = "Grise"

    var car2 = CarEntity("Seat", "Leon")
    var car3 = car

    println(car === car2)
    println(car === car3)
    var car4 = car.copy(model = "ibiza")

}