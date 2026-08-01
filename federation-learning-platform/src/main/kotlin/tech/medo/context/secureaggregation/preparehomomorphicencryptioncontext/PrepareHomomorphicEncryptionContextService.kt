package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import java.util.UUID;

interface PrepareHomomorphicEncryptionContextService {
    fun supports(input: PrepareHomomorphicEncryptionContextInput): Boolean = true
    fun execute(input: PrepareHomomorphicEncryptionContextInput): PrepareHomomorphicEncryptionContextResult
}

data class PrepareHomomorphicEncryptionContextInput(
    val secureAggregationSessionId: UUID,
    val encryptionScheme: String,
    val publicKeyVersion: String,
    val encryptedParameterScale: Int
)

sealed interface PrepareHomomorphicEncryptionContextResult {
    class Succeeded : PrepareHomomorphicEncryptionContextResult


}
