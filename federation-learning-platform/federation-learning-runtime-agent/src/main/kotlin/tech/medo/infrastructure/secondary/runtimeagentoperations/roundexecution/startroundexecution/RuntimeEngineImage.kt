package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

internal fun runtimeEngineImageReference(image: String, digest: String?): String {
    require(image.isNotBlank()) { "Execution plan runtime engine image is required." }
    if (digest.isNullOrBlank()) return image
    require(Regex("sha256:[a-fA-F0-9]{64}").matches(digest)) { "Invalid runtime engine image digest." }
    val pinnedDigest = image.substringAfter('@', "")
    require(pinnedDigest.isEmpty() || pinnedDigest.equals(digest, ignoreCase = true)) {
        "Runtime engine image does not match the execution plan digest."
    }
    return image.substringBefore('@') + "@" + digest
}
