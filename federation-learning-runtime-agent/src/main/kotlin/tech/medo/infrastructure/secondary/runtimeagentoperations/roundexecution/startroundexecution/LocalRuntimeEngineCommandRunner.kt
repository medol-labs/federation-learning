package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

interface LocalRuntimeEngineCommandRunner {
    fun run(properties: LocalRuntimeEngineProperties, arguments: List<String>): LocalRuntimeEngineCommandResult
}

@Component
class DockerComposeLocalRuntimeEngineCommandRunner : LocalRuntimeEngineCommandRunner {
    override fun run(properties: LocalRuntimeEngineProperties, arguments: List<String>): LocalRuntimeEngineCommandResult {
        val command = listOf("docker", "compose") +
            properties.projectName.takeIf { it.isNotBlank() }?.let { listOf("-p", it) }.orEmpty() +
            listOf("-f", properties.composeFile) +
            arguments

        log.debug("Running local runtime engine command: {}", command.joinToString(" "))
        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        val output = StringBuilder()
        val readerThread = Thread {
            process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { output.appendLine(it) }
            }
        }
        readerThread.isDaemon = true
        readerThread.start()

        val timeout = properties.commandTimeout.coerceAtLeast(Duration.ofSeconds(1))
        val completed = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS)
        if (!completed) {
            process.destroyForcibly()
            return LocalRuntimeEngineCommandResult(
                exitCode = -1,
                output = output.toString().trim(),
                timedOut = true
            )
        }

        readerThread.join(1000)
        return LocalRuntimeEngineCommandResult(
            exitCode = process.exitValue(),
            output = output.toString().trim(),
            timedOut = false
        )
    }

    private companion object {
        private val log = LoggerFactory.getLogger(DockerComposeLocalRuntimeEngineCommandRunner::class.java)
    }
}

data class LocalRuntimeEngineCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}
