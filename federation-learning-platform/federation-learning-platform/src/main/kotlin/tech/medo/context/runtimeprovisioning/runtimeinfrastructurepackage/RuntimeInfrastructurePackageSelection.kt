package tech.medo.runtimeprovisioning.runtimeinfrastructurepackage



data class RuntimeInfrastructurePackageSelection(
    val packageName: String,
    val packageVersion: String
)

object RuntimeInfrastructurePackageTags {
    const val PACKAGE_NAME = "packageName"
    const val PACKAGE_VERSION = "packageVersion"
}

object RuntimeInfrastructurePackageMetadata {
    val concepts = listOf("RuntimeInfrastructurePackage")
}
