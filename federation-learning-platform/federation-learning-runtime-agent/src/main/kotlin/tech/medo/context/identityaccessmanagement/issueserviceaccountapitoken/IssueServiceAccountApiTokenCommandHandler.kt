package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenCommand
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenInput
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenService




@Component
class IssueServiceAccountApiTokenCommandHandler(
    private val decision: IssueServiceAccountApiTokenDecision,
    private val issueServiceAccountApiTokenService: IssueServiceAccountApiTokenService
) {
    @CommandHandler
    fun handle(
        command: IssueServiceAccountApiTokenCommand,
        eventAppender: EventAppender
    ): IssueServiceAccountApiTokenResult {
        val input = IssueServiceAccountApiTokenInput(apiTokenId = command.apiTokenId, userAccountId = command.userAccountId, tokenName = command.tokenName)
        val portResult = issueServiceAccountApiTokenService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
        return portResult
    }
}
