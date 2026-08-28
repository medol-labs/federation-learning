package tech.medo.identityaccessmanagement.assignroletouser

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.useraccount.UserAccountSelection
import java.util.UUID;


@Command
data class AssignRoleToUserCommand(
    val userAccountId: UUID,
    val roleCode: String
) {
    @TargetEntityId
    val selection: UserAccountSelection = UserAccountSelection(userAccountId = userAccountId)

}
