package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.preparehomomorphicencryptioncontext

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextInput
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextResult
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextService

@Component
class LocalPrepareHomomorphicEncryptionContextAdapter : PrepareHomomorphicEncryptionContextService {
    private val log = LoggerFactory.getLogger(LocalPrepareHomomorphicEncryptionContextAdapter::class.java)

    override fun supports(input: PrepareHomomorphicEncryptionContextInput): Boolean = true

    override fun execute(input: PrepareHomomorphicEncryptionContextInput): PrepareHomomorphicEncryptionContextResult {
        log.info(
            "Prepared homomorphic encryption context locally. secureAggregationSessionId={}, selectedRuntimeCount={}",
            input.secureAggregationSessionId,
            input.selectedRuntimeCount
        )
        return PrepareHomomorphicEncryptionContextResult.Succeeded(
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
            encryptedParameterScale = 1000000
        )
    }
}
