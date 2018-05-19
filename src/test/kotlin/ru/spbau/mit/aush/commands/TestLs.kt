package ru.spbau.mit.aush.commands

import org.junit.Test

class TestLs : TestCommand("ls") {
    @Test
    fun currentDirectoryTest() {
        val testInput = "test input"
        val expectedOutput = """
            |Files in Software-Design-6th-semester
            |out
            |README.md
            |gradle
            |GrepArgparseLibraryChoiceExplained.md
            |gradlew
            |.gitignore
            |build.gradle
            |diagram.uml
            |.gradle
            |ArchReview.md
            |build
            |.git
            |gradlew.bat
            |settings.gradle
            |diagram.png
            |.idea
            |src
            |
            |
            |
            """.trimMargin()

        runTest(
                emptyList(),
                expectedOutput,
                testInput
        )
    }

    @Test
    fun dotCurrentDirectoryTest() {
        val testInput = "test input"
        val expectedOutput = """
            |Files in .
            |out
            |README.md
            |gradle
            |GrepArgparseLibraryChoiceExplained.md
            |gradlew
            |.gitignore
            |build.gradle
            |diagram.uml
            |.gradle
            |ArchReview.md
            |build
            |.git
            |gradlew.bat
            |settings.gradle
            |diagram.png
            |.idea
            |src
            |
            |
            |
            """.trimMargin()

        runTest(
                listOf("."),
                expectedOutput,
                testInput
        )
    }

    @Test
    fun subDirectoryTest() {
        val testInput = "test input"
        val expectedOutputSrc = """
            |Files in src
            |test
            |main
            |
            |
            |
            """.trimMargin()

        runTest(
                listOf("src"),
                expectedOutputSrc,
                testInput
        )

        val expectedOutputMain = """
            |Files in main
            |kotlin
            |
            |
            |
            """.trimMargin()

        runTest(
                listOf("src/main"),
                expectedOutputMain,
                testInput
        )
    }

    @Test
    fun doubleDirectoryTest() {
        val testInput = "test input"
        val expectedOutputMain = """
            |Files in main
            |kotlin
            |
            |
            """.trimMargin()

        val expectedOutput = expectedOutputMain + expectedOutputMain + "\n"

        val arg = "src/main"
        runTest(
                listOf(arg, arg),
                expectedOutput,
                testInput
        )
    }

    @Test
    fun noDirectoryTest() {
        val testInput = "test input"
        val expectedOutput = """
            |directory ERRUQRJF not found or not exists
            |
            |
            """.trimMargin()
        val arg = "ERRUQRJF"

        runTest(
                listOf(arg),
                expectedOutput,
                testInput
        )
    }

    @Test
    fun fileTest() {
        val testInput = "test input"
        val arg = "build.gradle"
        val expectedOutput = """
            |$arg
            |
            |
            """.trimMargin()

        runTest(
                listOf(arg),
                expectedOutput,
                testInput
        )
    }

    @Test
    fun complexDirectoryTest() {
        val testInput = "test input"
        val expectedOutputMain = """
            |Files in main
            |kotlin
            |
            |
            |
            """.trimMargin()

        runTest(
                listOf("./src/../src/main"),
                expectedOutputMain,
                testInput
        )
    }
}