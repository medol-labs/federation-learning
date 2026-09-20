package tech.medo.domain.modelrepository.registermodelartifact

import org.springframework.stereotype.Component
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactDecision
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Component
class RegisterModelArtifactDecisionComponent : RegisterModelArtifactDecision {
    override fun decide(command: RegisterModelArtifactCommand): List<Any> {
        val modelFormat = command.modelFormat?.takeIf { it.isNotBlank() }
            ?: defaultModelFormat(command.modelName)
        val registryRef = command.modelName
        val artifactUri = if (command.sourceType.equals("BUILT_IN", ignoreCase = true)) {
            "builtin://runtime-engine/model/${command.modelName}:${command.modelVersion}"
        } else {
            command.fileId?.let { "file://$it" }
                ?: error("fileId is required when registering a non built-in model artifact.")
        }

        return listOf(
            ModelArtifactRegisteredEvent(
                modelId = command.modelId,
                modelName = command.modelName,
                modelPlugin = command.modelPlugin,
                modelVersion = command.modelVersion,
                modelDescription = command.modelDescription,
                sourceType = command.sourceType,
                modelArtifactUri = artifactUri,
                modelRegistryRef = registryRef,
                modelFormat = modelFormat,
                modelArtifactDigest = "sha256:${sha256Hex("${command.modelName}:${command.modelVersion}:$artifactUri")}",
                modelSignatureUri = null,
                modelSizeBytes = null,
            ),
        )
    }

    private fun defaultModelFormat(modelName: String): String =
        if (modelName.contains("SKLEARN", ignoreCase = true) ||
            modelName.contains("LogisticRegression", ignoreCase = true)
        ) {
            "JSON"
        } else {
            "PYTORCH_STATE_DICT"
        }

    private fun sha256Hex(value: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
}
