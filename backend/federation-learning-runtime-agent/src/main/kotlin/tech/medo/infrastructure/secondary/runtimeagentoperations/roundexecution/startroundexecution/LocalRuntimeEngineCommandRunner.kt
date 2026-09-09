package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.file.Path
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
            .apply {
                environment().putAll(properties.runtimeEngineComposeEnvironment())
            }
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

private fun LocalRuntimeEngineProperties.runtimeEngineComposeEnvironment(): Map<String, String> =
    buildMap {
        if (datasetHostRoot.isNotBlank()) {
            put("RUNTIME_ENGINE_DATASETS_DIR", datasetHostRoot.toAbsolutePathString())
        }
        if (runtimeRootHostRoot.isNotBlank()) {
            put("RUNTIME_ENGINE_TMP_DIR", runtimeTmpHostRoot())
        }
        if (runtimeRoot.isNotBlank()) {
            put("RUNTIME_ENGINE_ROOT", runtimeRoot)
        }
        if (nodeName.isNotBlank()) {
            put("RUNTIME_ENGINE_NODE_NAME", nodeName)
        }
    }

private fun LocalRuntimeEngineProperties.runtimeTmpHostRoot(): String {
    val containerTmpRoot = "/workspace/tmp"
    val normalizedRuntimeRoot = runtimeRoot.trimEnd('/')
    if (!normalizedRuntimeRoot.startsWith("$containerTmpRoot/")) {
        return runtimeRootHostRoot
    }

    val suffix = normalizedRuntimeRoot.removePrefix("$containerTmpRoot/").trim('/')
    if (suffix.isBlank()) {
        return runtimeRootHostRoot
    }

    val suffixSegments = suffix.split('/').filter { it.isNotBlank() }
    return suffixSegments.fold(Path.of(runtimeRootHostRoot).toAbsolutePath().normalize()) { path, _ ->
        path.parent ?: path
    }.toString()
}

private fun String.toAbsolutePathString(): String =
    Path.of(this).toAbsolutePath().normalize().toString()

data class LocalRuntimeEngineCommandResult(
    val exitCode: Int,
    val output: String,
    val timedOut: Boolean
) {
    val succeeded: Boolean = exitCode == 0 && !timedOut
}
