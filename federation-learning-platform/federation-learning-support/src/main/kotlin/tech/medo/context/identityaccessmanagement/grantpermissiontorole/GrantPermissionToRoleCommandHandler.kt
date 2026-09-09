package tech.medo.identityaccessmanagement.grantpermissiontorole

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand

import tech.medo.identityaccessmanagement.role.RoleState



@Component
class GrantPermissionToRoleCommandHandler(
    private val decision: GrantPermissionToRoleDecision
) {
    @CommandHandler
    fun handle(
        command: GrantPermissionToRoleCommand,
        @InjectEntity(idProperty = "roleCode") state: RoleState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
