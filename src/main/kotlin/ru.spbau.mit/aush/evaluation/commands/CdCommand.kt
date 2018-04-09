package ru.spbau.mit.aush.evaluation.commands

import ru.spbau.mit.aush.evaluation.*
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Represents simplified version of `cd` Bash command
 * There is no output, but it's changing current user directory
 * Throws exceptions if directory not found or given directory is a file.
 * Also, there is an exception for too many arguments: for cd command we assume only 1 argument
 */
internal object CdCommand : Command() {
    /**
     * {@inheritDoc}
     */
    override fun evaluate(args: List<String>, environment: Environment) {
        if (args.size > 1) {
            throw TooManyArgumentsException("Too many arguments for cd command!")
        }

        var directory: Path?
        if (args.isEmpty()) {
            directory = Paths.get(System.getProperty("user.home"))
        } else {
            directory = Paths.get(args[0]).normalize()
            if (!directory.isAbsolute) {
                directory = environment.userDir.resolve(directory)
            }
        }

        val file = directory.toFile()
        val name = file.name

        if (!file.exists()) {
            throw DirectoryNotFoundException("Directory $name not found or not exists")
        }

        if (!file.isDirectory) {
            throw FileIsNotDirectoryException("File $name is not a directory")
        }

        environment.userDir = directory
    }
}