package tech.medo.identityaccessmanagement.infrastructure.secondary.serviceaccountapitoken.routing

import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenInput
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenService
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class IssueServiceAccountApiTokenServiceRouter(private val adapters: ObjectProvider<IssueServiceAccountApiTokenService>) : IssueServiceAccountApiTokenService {
    override fun supports(input: IssueServiceAccountApiTokenInput): Boolean = true

    override fun execute(input: IssueServiceAccountApiTokenInput): IssueServiceAccountApiTokenResult {
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
            0 -> error("No IssueServiceAccountApiTokenService adapter supports the requested input.")
            else -> error("Multiple IssueServiceAccountApiTokenService adapters support the requested input.")
        }
    }
}
