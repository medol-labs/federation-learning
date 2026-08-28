package tech.medo.identityaccessmanagement.deactivateuseraccount

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.useraccount.UserAccountSelection
import java.util.UUID;


@Command
data class DeactivateUserAccountCommand(
    val userAccountId: UUID,
    val reason: String
) {
    @TargetEntityId
    val selection: UserAccountSelection = UserAccountSelection(userAccountId = userAccountId)

}
