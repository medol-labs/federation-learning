package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.springframework.stereotype.Component
import java.io.File
import java.time.Duration
import java.util.concurrent.TimeUnit

@Component
class DockerComposeCommandRunner {
    fun run(properties: DockerComposeRuntimeInfrastructureProperties, arguments: List<String>): DockerComposeCommandResult {
        val command = listOf("docker", "compose", "-f", properties.composeFile) + arguments
        val process = ProcessBuilder(command)
            .directory(properties.projectDirectory?.takeIf { it.isNotBlank() }?.let(::File))
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
            return DockerComposeCommandResult(
                exitCode = -1,
                output = output.toString().trim(),
                timedOut = true
            )
        }
        readerThread.join(1000)
        return DockerComposeCommandResult(
            exitCode = process.exitValue(),
            output = output.toString().trim(),
            timedOut = false
        )
    }
}

data class DockerComposeCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}
