package de.ungerts.quarkus.plugin


import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

import java.nio.file.Files
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.*

class QuarkusJibPluginTest {

    @Test
    void testJibImageTarBuild(@TempDir Path tempProjectRoot) {
        Path buildFile = tempProjectRoot.resolve('build.gradle')
        Path mocklib = tempProjectRoot.resolve("build${File.separator}lib${File.separator}mocklib.jar")
        Files.createDirectories(mocklib.getParent())
        Files.createFile(mocklib)
        Files.createFile(tempProjectRoot.resolve("build${File.separator}test-runner.jar"))

        InputStream buildFileContent =
                getClass().getClassLoader().getResourceAsStream('gradle/configuration/build.gradle')
        Files.copy(buildFileContent, buildFile)

        def result = GradleRunner.create()
                .withProjectDir(tempProjectRoot.toFile())
                .withPluginClasspath()
                .withGradleVersion('6.0.1')
                .withArguments('jibImageTar')
                .build()
        println "Output: ${System.lineSeparator()}${result.output}"
        assertTrue(result.task(':jibImageTar').outcome == TaskOutcome.SUCCESS)
        Path imagePath = tempProjectRoot.resolve("build${File.separator}runner-image.tar")
        assertTrue(Files.isRegularFile(imagePath))
    }


}
