package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

@Component
class ProcessK3sCommandRunner : K3sCommandRunner {
    override fun run(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult {
        val command = buildList {
            add(properties.kubectlExecutable)
            properties.kubeconfig?.takeIf { it.isNotBlank() }?.let {
                add("--kubeconfig")
                add(it)
            }
            addAll(arguments)
        }
        log.debug("Running K3S command command={}", command.joinToString(" "))
        val process = ProcessBuilder(command)
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
            val result = K3sCommandResult(
                exitCode = -1,
                output = output.toString().trim(),
                timedOut = true
            )
            log.debug(
                "K3S command timed out timeout={}, output={}",
                timeout,
                result.output.truncateForLog()
            )
            return result
        }
        readerThread.join(1000)
        val result = K3sCommandResult(
            exitCode = process.exitValue(),
            output = output.toString().trim(),
            timedOut = false
        )
        log.debug(
            "K3S command completed exitCode={}, succeeded={}, output={}",
            result.exitCode,
            result.succeeded,
            result.output.truncateForLog()
        )
        return result
    }

    companion object {
        private val log = LoggerFactory.getLogger(ProcessK3sCommandRunner::class.java)
    }
}

interface K3sCommandRunner {
    fun run(properties: K3sRuntimeInfrastructureProperties, arguments: List<String>): K3sCommandResult
}

data class K3sCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}

private fun String.truncateForLog(maxLength: Int = 4000): String =
    if (length <= maxLength) this else "${take(maxLength)}...<truncated>"
