package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset

import java.nio.file.Files
import java.nio.file.Path

fun resolveRuntimeDatasetPath(
    filePath: String,
    datasetHostRoot: String,
    datasetContainerRoot: String
): Path {
    val directPath = Path.of(filePath)
    if (Files.exists(directPath)) {
        return directPath
    }

    val relativePath = containerRelativePath(filePath, datasetContainerRoot) ?: return directPath
    return datasetHostRootCandidates(datasetHostRoot)
        .map { Path.of(it).resolve(relativePath).toAbsolutePath().normalize() }
        .firstOrNull { Files.exists(it) }
        ?: Path.of(datasetHostRoot).resolve(relativePath).toAbsolutePath().normalize()
}

private fun containerRelativePath(filePath: String, datasetContainerRoot: String): String? {
    val normalizedPath = normalizePath(filePath)
    val normalizedRoot = normalizePath(datasetContainerRoot).trimEnd('/')
    return when {
        normalizedRoot.isBlank() -> null
        normalizedPath == normalizedRoot -> ""
        normalizedPath.startsWith("$normalizedRoot/") -> normalizedPath.removePrefix("$normalizedRoot/")
        else -> null
    }
}

private fun datasetHostRootCandidates(datasetHostRoot: String): List<String> =
    listOf(
        datasetHostRoot,
        "../volumes/datasets",
        "../../volumes/datasets"
    ).map { it.trim() }
        .filter { it.isNotBlank() }
        .distinct()

private fun normalizePath(value: String): String =
    value.trim().replace('\\', '/')
