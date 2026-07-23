package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionSelection
import java.util.UUID;


@Command
data class PrepareHomomorphicEncryptionContextCommand(
    val secureAggregationSessionId: UUID,
    val encryptionScheme: String,
    val publicKeyVersion: String,
    val encryptedParameterScale: Int
) {
    @TargetEntityId
    val selection: SecureAggregationSessionSelection = SecureAggregationSessionSelection(secureAggregationSessionId = secureAggregationSessionId)

}
