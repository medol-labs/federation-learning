package tech.medo.identityaccessmanagement.registerrole

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.role.RoleSelection


@Command
data class RegisterRoleCommand(
    val roleCode: String,
    val roleName: String
) {
    @TargetEntityId
    val selection: RoleSelection = RoleSelection(roleCode = roleCode)

}
