package tech.medo.runtimegovernance.domain

object Concepts {
    data object RuntimeIdentity {
        const val NAME = "RuntimeIdentity"
        val slices = listOf("ActivateRuntimeIdentity", "RevokeRuntimeIdentity", "RuntimeIdentityCatalog")
        val states = listOf("Active", "Revoked")
    }

    data object RuntimeCapability {
        const val NAME = "RuntimeCapability"
        val slices = listOf("DetectRuntimeCapabilities", "RuntimeCapabilityCatalog")
        val states = listOf("Detected")
    }
}
