package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.time.Duration
import java.util.concurrent.TimeUnit

@Component
class ProcessDockerComposeCommandRunner : DockerComposeCommandRunner {
    override fun run(properties: DockerComposeRuntimeInfrastructureProperties, arguments: List<String>): DockerComposeCommandResult {
        val command = listOf("docker", "compose", "-f", properties.composeFile) + arguments
        val workingDirectory = properties.projectDirectory?.takeIf { it.isNotBlank() }?.let(::File)
        log.debug("Running Docker Compose command cwd={}, command={}", workingDirectory?.absolutePath, command.joinToString(" "))
        val process = ProcessBuilder(command)
            .directory(workingDirectory)
            .redirectErrorStream(true)
            .start()

        val output = StringBuilder()
        val readerThread = Thread {
            process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line -> output.appendLine(line) }
            }
        }
        readerThread.isDaemon = true
        readerThread.start()

        val timeout = properties.commandTimeout.coerceAtLeast(Duration.ofSeconds(1))
        val completed = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS)
        if (!completed) {
            process.destroyForcibly()
            val result = DockerComposeCommandResult(
                exitCode = -1,
                output = output.toString().trim(),
                timedOut = true
            )
            log.debug(
                "Docker Compose command timed out timeout={}, output={}",
                timeout,
                result.output.truncateForLog()
            )
            return result
        }
        readerThread.join(1000)
        val result = DockerComposeCommandResult(
            exitCode = process.exitValue(),
            output = output.toString().trim(),
            timedOut = false
        )
        log.debug(
            "Docker Compose command completed exitCode={}, succeeded={}, output={}",
            result.exitCode,
            result.succeeded,
            result.output.truncateForLog()
        )
        return result
    }

    companion object {
        private val log = LoggerFactory.getLogger(ProcessDockerComposeCommandRunner::class.java)
    }
}

interface DockerComposeCommandRunner {
    fun run(properties: DockerComposeRuntimeInfrastructureProperties, arguments: List<String>): DockerComposeCommandResult
}

data class DockerComposeCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}

private fun String.truncateForLog(maxLength: Int = 4000): String =
    if (length <= maxLength) this else "${take(maxLength)}...<truncated>"
