package tech.medo.identityaccessmanagement.grantpermissiontorole

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.role.RoleSelection
import java.util.UUID;


@Command
data class GrantPermissionToRoleCommand(
    val roleId: UUID,
    val roleCode: String,
    val permissionCodes: List<String>
) {
    @TargetEntityId
    val selection: RoleSelection = RoleSelection(roleCode = roleCode.trim().lowercase())

}
