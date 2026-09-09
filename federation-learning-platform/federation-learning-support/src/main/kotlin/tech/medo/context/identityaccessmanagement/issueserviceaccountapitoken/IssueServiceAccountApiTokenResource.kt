package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory

import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/serviceaccountapitoken")
class IssueServiceAccountApiTokenResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('issue_service_account_api_token:execute')")
    @PostMapping("/issueserviceaccountapitoken")
    fun IssueServiceAccountApiToken(
        @Valid @RequestBody command: IssueServiceAccountApiTokenCommand,
        request: HttpServletRequest
    ): CompletableFuture<IssueServiceAccountApiTokenResult> =
        commandGateway.send(command, MetadataFactory.from(request)).resultAs(IssueServiceAccountApiTokenResult::class.java)
}
