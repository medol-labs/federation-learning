package tech.medo.identityaccessmanagement.generateuseraccountloginpassword

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.useraccount.UserAccountSelection
import java.util.UUID;


@Command
data class GenerateUserAccountLoginPasswordCommand(
    val userAccountId: UUID,
    val passwordResetRequired: Boolean
) {
    @TargetEntityId
    val selection: UserAccountSelection = UserAccountSelection(userAccountId = userAccountId)

}
