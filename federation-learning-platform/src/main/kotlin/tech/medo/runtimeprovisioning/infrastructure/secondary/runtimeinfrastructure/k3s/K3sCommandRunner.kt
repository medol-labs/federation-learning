package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.k3s

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
            return K3sCommandResult(
                exitCode = -1,
                output = output.toString().trim(),
                timedOut = true
            )
        }
        readerThread.join(1000)
        return K3sCommandResult(
            exitCode = process.exitValue(),
            output = output.toString().trim(),
            timedOut = false
        )
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
