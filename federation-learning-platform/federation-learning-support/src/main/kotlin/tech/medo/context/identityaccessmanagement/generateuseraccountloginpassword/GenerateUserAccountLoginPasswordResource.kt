package tech.medo.identityaccessmanagement.generateuseraccountloginpassword

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

import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordResult
import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/useraccount")
class GenerateUserAccountLoginPasswordResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('generate_user_account_login_password:execute')")
    @PostMapping("/generateuseraccountloginpassword")
    fun GenerateUserAccountLoginPassword(
        @Valid @RequestBody command: GenerateUserAccountLoginPasswordCommand,
        request: HttpServletRequest
    ): CompletableFuture<GenerateUserAccountLoginPasswordResult> =
        commandGateway.send(command, MetadataFactory.from(request)).resultAs(GenerateUserAccountLoginPasswordResult::class.java)
}
