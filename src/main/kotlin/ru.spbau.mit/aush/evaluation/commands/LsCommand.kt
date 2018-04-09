package ru.spbau.mit.aush.evaluation.commands

import ru.spbau.mit.aush.evaluation.Environment
import java.io.File
import java.io.PrintStream
import java.nio.file.Paths

/**
 * Represents simplified version of `ls` Bash command
 * Outputs list of files of each specified directory, or prints, that given directory doesn't exist.
 */
internal object LsCommand : Command() {
    /**
     * {@inheritDoc}
     */
    override fun evaluate(args: List<String>, environment: Environment) {
        var lsMessage = ""

        if (args.isEmpty()) {
            lsMessage = getFilesMessage(environment.userDir.toFile())
        } else {
            for (arg in args) {
                val directory = Paths.get(arg)
                if (directory.isAbsolute) {
                    lsMessage += getFilesMessage(directory.toFile())
                } else {
                    val resolvedDirectory = environment.userDir.resolve(directory).toFile()

                    if (resolvedDirectory.exists()) {
                        lsMessage += getFilesMessage(resolvedDirectory)
                    } else {
                        lsMessage += notExistsMessage(resolvedDirectory)
                    }
                }
            }
        }

        PrintStream(environment.io.output).println(lsMessage)
    }

    /**
     * Returns message of non-existing directory
     */
    private fun notExistsMessage(directory: File): String {
        val name = directory.name

        return "directory $name not found or not exists\n"
    }

    /**
     * Returns list of files as string
     */
    private fun getFilesMessage(directory: File): String {
        if (!directory.isDirectory) {
            return directory.name + "\n"
        }

        var message = "Files in " + directory.name + "\n"

        for (file in directory.listFiles()) {
            message += file.name + "\n"
        }

        message += '\n'
        return message
    }
}