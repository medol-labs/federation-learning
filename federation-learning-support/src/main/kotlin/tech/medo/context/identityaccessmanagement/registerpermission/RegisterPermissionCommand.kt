package tech.medo.identityaccessmanagement.registerpermission

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.permission.PermissionSelection


@Command
data class RegisterPermissionCommand(
    val permissionCode: String,
    val permissionName: String,
    val description: String?
) {
    @TargetEntityId
    val selection: PermissionSelection = PermissionSelection(permissionCode = permissionCode)

}
