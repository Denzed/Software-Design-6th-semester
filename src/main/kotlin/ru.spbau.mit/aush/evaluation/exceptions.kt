package ru.spbau.mit.aush.evaluation

/**
 * Represents exceptions occurred in evaluation process
 */
sealed class EvaluationException(
        message: String? = null,
        cause: Throwable? = null) : Exception(message, cause)

/**
 * Represents an cause in evaluation of a specific command
 */
class CommandEvaluationFailedException(
        command: String,
        cause: Throwable
) : EvaluationException(
        """
            |command "$command" evaluation failed because
            |${cause.message ?: cause}
        """.trimMargin(),
        cause
)

/**
 * Represents exception, when directory is not found during cd command
 */
class DirectoryNotFoundException(message: String) : Exception(message)

/**
 * Represents exception, when file is not a file during cd command
 */
class FileIsNotDirectoryException(message: String) : Exception(message)

/**
 * Represents exception, when passed more than 1 argument to cd command
 */
class TooManyArgumentsException(message: String) : Exception(message)