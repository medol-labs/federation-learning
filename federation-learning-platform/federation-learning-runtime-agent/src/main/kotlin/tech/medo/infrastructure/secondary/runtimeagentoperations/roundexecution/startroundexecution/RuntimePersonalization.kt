package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import tech.medo.runtimeagentoperations.startroundexecution.StartRoundExecutionInput

internal fun StartRoundExecutionInput.personalizationParameters(): Map<String, Any?> {
    val mode = personalizationMode ?: "GLOBAL_ONLY"
    require(mode in setOf("GLOBAL_ONLY", "LOCAL_ADAPTATION")) { "Unsupported personalization mode: $mode" }
    if (mode != "GLOBAL_ONLY") {
        require(personalizationScope == "PARTICIPANT" && personalizationTargetType == "PARTICIPANT" && personalizationTargetId == runtimeId) {
            "Local adaptation currently supports participant-scoped targets bound to the executing runtime."
        }
        require(personalizationTargetId != null && !personalizationTargetType.isNullOrBlank()) {
            "Personalized execution requires a target type and identifier."
        }
        require(personalizedArtifactPolicy in setOf("REGISTER_RUNTIME_LOCAL", "KEEP_AT_RUNTIME")) {
            "Local adaptation currently requires runtime-local personalized artifact storage."
        }
    }
    return mapOf(
        "personalizationMode" to mode,
        "personalizationScope" to personalizationScope,
        "personalizedArtifactPolicy" to personalizedArtifactPolicy,
        "personalizationMetric" to personalizationMetric,
        "minimumPersonalizationLift" to minimumPersonalizationLift,
        "personalizationTargetType" to personalizationTargetType,
        "personalizationTargetId" to personalizationTargetId?.toString(),
        "personalizationCohortId" to personalizationCohortId?.toString(),
        "personalizationProfileId" to personalizationProfileId?.toString()
    )
}

internal fun personalizedOutputs(outputRoot: String): Map<String, String> = mapOf(
    "personalizedModel" to "$outputRoot/personalized_model_state_dict.pt",
    "personalizedMetrics" to "$outputRoot/personalized_metrics.json"
)
