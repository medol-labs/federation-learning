package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.serviceaccountapitoken.ServiceAccountApiTokenSelection
import java.util.UUID;


@Command
data class IssueServiceAccountApiTokenCommand(
    val apiTokenId: UUID = java.util.UUID.randomUUID(),
    val userAccountId: UUID,
    val tokenName: String
) {
    @TargetEntityId
    val selection: ServiceAccountApiTokenSelection = ServiceAccountApiTokenSelection(userAccountId = userAccountId)

}
