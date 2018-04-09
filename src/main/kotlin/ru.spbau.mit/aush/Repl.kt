package ru.spbau.mit.aush

import ru.spbau.mit.aush.ast.ASTNode
import ru.spbau.mit.aush.evaluation.*
import ru.spbau.mit.aush.lexer.*
import ru.spbau.mit.aush.parser.CommandListParser
import java.io.PrintStream
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Represents the shell running with given streams
 */
class Repl(
        private val environmentIO: EnvironmentIO
) {
    private val input = environmentIO.input.bufferedReader()

    private val output = PrintStream(environmentIO.output)

    private var environmentVariables = EnvironmentVariables.emptyVariables

    private var userDir: Path = Paths.get(System.getProperty("user.dir"))

    private fun greet() =
            output.println("Welcome to AUsh (Academic University SHell)")

    private fun promptInput() {
        output.print("> ")
    }

    private fun readLine(): String? {
        promptInput()
        return input.readLine()
    }

    private fun readCommand(): ASTNode? {
        var commandsString = readLine() ?: return null
        var lexResult = Lexer.tryLex(commandsString)
        while (lexResult is LexFailure
                && (lexResult.cause is UnclosedQuoteException
                        || lexResult.cause == NoCommandAfterPipeException)) {
            commandsString += "\n" + readLine()
            lexResult = Lexer.tryLex(commandsString)
        }
        return when (lexResult) {
            is LexFailure -> throw lexResult.cause
            is LexSuccess -> CommandListParser.parse(lexResult.commands)
        }
    }

    private tailrec fun runInner() {
        try {
            val command = readCommand() ?: return
            val environment = Environment(environmentVariables, environmentIO, userDir)

            val result = command.evaluate(environment)
            userDir = environment.userDir

            when (result) {
                is EvaluationSuccess -> {
                    environmentVariables += result.modifiedEnvironmentVariables
                }
                SuccessfullyExited -> return
                is EvaluationFailure ->
                    throw CommandEvaluationFailedException(
                            result.command,
                            result.cause
                    )
            }
        } catch (throwable: Throwable) {
            output.println(
                    """
                        |Evaluation failed because
                        |${throwable.message ?: throwable}
                    """.trimMargin()
            )
        }
        runInner()
    }

    /**
     * Greets the user and runs the main shell loop
     */
    fun run() {
        greet()
        runInner()
    }
}