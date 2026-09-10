package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import java.util.UUID;
import java.math.BigDecimal;

interface PrepareHomomorphicEncryptionContextService {
    fun supports(input: PrepareHomomorphicEncryptionContextInput): Boolean = true
    fun execute(input: PrepareHomomorphicEncryptionContextInput): PrepareHomomorphicEncryptionContextResult
}

data class PrepareHomomorphicEncryptionContextInput(
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val federationId: UUID,
    val federationName: String?,
    val trainingRunConfigurationId: UUID,
    val configurationName: String?,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val roundId: UUID,
    val roundNumber: Int,
    val selectedOrganizationIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedOrganizationCount: Int,
    val selectedRuntimeCount: Int,
    val minimumNodesPerRound: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
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
