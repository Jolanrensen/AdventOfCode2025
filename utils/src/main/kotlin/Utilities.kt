package nl.jolanrensen.aoc25.utils

import kotlinx.coroutines.*
import kotlinx.datetime.*
import kotlinx.serialization.Serializable

@Serializable
class Printer(
    val message: String,
) {
    fun printMessage() =
        runBlocking {
            val now: Instant = Clock.System.now()
            launch {
                delay(1000L)
                println(now.toString())
            }
            println(message)
        }
}
