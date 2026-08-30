package tech.medo.identityaccessmanagement.registerpermission

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.identityaccessmanagement.permission.PermissionSelection
import java.util.UUID;


@Command
data class RegisterPermissionCommand(
    val permissionId: UUID = java.util.UUID.randomUUID(),
    val permissionCode: String,
    val permissionName: String,
    val description: String?
) {
    @TargetEntityId
    val selection: PermissionSelection = PermissionSelection(permissionCode = permissionCode.trim().lowercase())

}
