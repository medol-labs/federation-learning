package tech.medo.secureaggregation.infrastructure.secondary.secureaggregationsession.routing

import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextInput
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextService
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class PrepareHomomorphicEncryptionContextServiceRouter(private val adapters: ObjectProvider<PrepareHomomorphicEncryptionContextService>) : PrepareHomomorphicEncryptionContextService {
    override fun supports(input: PrepareHomomorphicEncryptionContextInput): Boolean = true

    override fun execute(input: PrepareHomomorphicEncryptionContextInput): PrepareHomomorphicEncryptionContextResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No PrepareHomomorphicEncryptionContextService adapter supports the requested input.")
            else -> error("Multiple PrepareHomomorphicEncryptionContextService adapters support the requested input.")
        }
    }
}
