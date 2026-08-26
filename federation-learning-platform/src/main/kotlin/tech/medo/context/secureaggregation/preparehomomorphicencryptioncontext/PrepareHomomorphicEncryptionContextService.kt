package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import java.util.UUID;

interface PrepareHomomorphicEncryptionContextService {
    fun supports(input: PrepareHomomorphicEncryptionContextInput): Boolean = true
    fun execute(input: PrepareHomomorphicEncryptionContextInput): PrepareHomomorphicEncryptionContextResult
}

data class PrepareHomomorphicEncryptionContextInput(
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val secureAggregationRequired: Boolean
)

sealed interface PrepareHomomorphicEncryptionContextResult {
    data class Succeeded(
        val encryptionScheme: String,
        val publicKeyVersion: String,
        val publicKeyRef: String,
        val encryptedParameterScale: Int
    ) : PrepareHomomorphicEncryptionContextResult


}
