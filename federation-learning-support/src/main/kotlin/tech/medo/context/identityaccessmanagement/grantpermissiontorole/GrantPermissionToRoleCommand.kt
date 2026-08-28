package tech.medo.identityaccessmanagement.grantpermissiontorole

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.role.RoleSelection


@Command
data class GrantPermissionToRoleCommand(
    val roleCode: String,
    val permissionCode: String
) {
    @TargetEntityId
    val selection: RoleSelection = RoleSelection(roleCode = roleCode)

}
