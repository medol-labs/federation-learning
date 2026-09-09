package tech.medo.identityaccessmanagement.registerrole

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.role.RoleSelection
import java.util.UUID;


@Command
data class RegisterRoleCommand(
    val roleId: UUID = java.util.UUID.randomUUID(),
    val roleCode: String,
    val roleName: String
) {
    @TargetEntityId
    val selection: RoleSelection = RoleSelection(roleCode = roleCode.trim().lowercase())

}
