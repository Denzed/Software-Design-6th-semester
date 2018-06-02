package ru.spbau.mit.roguelike

import ru.spbau.mit.roguelike.ui.GameUI
import ru.spbau.mit.roguelike.ui.cli.CLIGameUI

/**
 * All available game modes in form `argument-to-main` -> (`usage-entry`, `game-mode`)
 */
private val gameModes = mapOf<String,Pair<String,GameUI>>(
        "cli" to ("CLI" to CLIGameUI)
)

private const val defaultGameMode = "cli"

/**
 * Usage message for main function
 */
private val usageMessage: String by lazy {
    StringBuilder()
            .appendln("Usage:")
            .appendln(
                    gameModes.toList().joinToString("\n") {
                        val defaultSuffix = if (it.first == defaultGameMode) " [default]" else ""

                        "${it.first} for ${it.second.first} game mode$defaultSuffix"
                    }
            )
            .toString()
}

/**
 * Outputs usage message with some additional information
 * @param additionalMessage additional information to output before usage message
 */
private fun printUsage(additionalMessage: String = "") {
    println("""
        $additionalMessage
        $usageMessage
    """.trimIndent())
}

/**
 * Application entry point
 */
fun main(args: Array<String>) {
    when {
        args.size > 1  -> {
            printUsage("Too many arguments")
        }
        else           -> {
                val gameMode =
                        if (args.isEmpty()) {
                            println("No game mode supplied. Using default: \"$defaultGameMode\"")
                            defaultGameMode
                        } else {
                            args.first()
                        }
                gameModes[gameMode]
                        ?.second
                        ?.main(args.drop(1).toTypedArray())
                        ?: printUsage("Unknown game mode \"$gameMode\"")
        }
    }
}