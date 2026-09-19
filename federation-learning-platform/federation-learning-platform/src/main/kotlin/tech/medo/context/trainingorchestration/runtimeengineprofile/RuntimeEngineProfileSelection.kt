package tech.medo.trainingorchestration.runtimeengineprofile



data class RuntimeEngineProfileSelection(
    val profileName: String
)

object RuntimeEngineProfileTags {
    const val PROFILE_NAME = "profileName"
}

object RuntimeEngineProfileMetadata {
    val concepts = listOf("RuntimeEngineProfile")
}
