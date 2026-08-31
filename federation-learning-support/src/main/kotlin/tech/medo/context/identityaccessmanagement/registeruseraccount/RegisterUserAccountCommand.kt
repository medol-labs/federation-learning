package tech.medo.identityaccessmanagement.registeruseraccount

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.useraccount.UserAccountSelection
import java.util.UUID;


@Command
data class RegisterUserAccountCommand(
    val userAccountId: UUID = java.util.UUID.randomUUID(),
    val username: String,
    val providerSubject: String?,
    val passwordHash: String?
) {
    @TargetEntityId
    val selection: UserAccountSelection = UserAccountSelection(userAccountId = userAccountId)

}
