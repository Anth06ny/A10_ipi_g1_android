package fr.amonteiro.a10_ipi_g1_android.exo

import kotlin.concurrent.thread


fun main() {
    var res = boulangerie(nbBaguette = 5, nbSandwitch = 1)
    println("res=$res")
}

fun boulangerie(nbCroissant: Int = 0, nbSandwitch: Int = 0, nbBaguette: Int = 0) =
    PRICE_BAGUETTE * nbBaguette + PRICE_SANDWICH * nbSandwitch + PRICE_CROISSANT * nbCroissant