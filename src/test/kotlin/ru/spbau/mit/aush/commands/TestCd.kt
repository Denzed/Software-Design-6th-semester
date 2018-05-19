package ru.spbau.mit.aush.commands

import org.junit.Test
import ru.spbau.mit.aush.evaluation.DirectoryNotFoundException
import ru.spbau.mit.aush.evaluation.FileIsNotDirectoryException
import ru.spbau.mit.aush.evaluation.TooManyArgumentsException
import java.nio.file.Paths

class TestCd : TestCommand("cd") {
    private val curDir = Paths.get(System.getProperty("user.dir"))

    @Test
    fun emptyArgTest() {
        val testInput = "test input"

        runTest(
                emptyList(),
                "",
                testInput,
                expectedUserDirectory = Paths.get(System.getProperty("user.home"))
        )
    }

    @Test
    fun currentDirectoryTest() {
        val testInput = "test input"

        runTest(
                listOf("."),
                "",
                testInput,
                expectedUserDirectory = curDir
        )
    }

    @Test
    fun subDirectoryTest() {
        val testInput = "test input"

        runTest(
                listOf("src"),
                "",
                testInput,
                expectedUserDirectory = curDir.resolve("src")
        )
    }

    @Test
    fun complexDirectoryTest() {
        val testInput = "test input"

        runTest(
                listOf("src/kotlin/../"),
                "",
                testInput,
                expectedUserDirectory = curDir.resolve("src")
        )
    }

    @Test(expected = DirectoryNotFoundException::class)
    fun noSuchDirectoryTest() {
        val testInput = "test input"

        runTest(
                listOf("srcHJDSFL"),
                "",
                testInput
        )
    }

    @Test(expected = FileIsNotDirectoryException::class)
    fun fileTest() {
        val testInput = "test input"

        runTest(
                listOf("build.gradle"),
                "",
                testInput
        )
    }

    @Test(expected = TooManyArgumentsException::class)
    fun multipleArgumentsTest() {
        val testInput = "test input"

        runTest(
                listOf("src", "src"),
                "",
                testInput
        )
    }
}