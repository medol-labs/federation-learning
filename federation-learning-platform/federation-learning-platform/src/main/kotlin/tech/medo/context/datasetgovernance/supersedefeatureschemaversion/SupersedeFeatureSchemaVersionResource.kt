package tech.medo.datasetgovernance.supersedefeatureschemaversion

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory


import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/featureschema")
class SupersedeFeatureSchemaVersionResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('supersede_feature_schema_version:execute')")
    @PostMapping("/supersedefeatureschemaversion")
    fun SupersedeFeatureSchemaVersion(
        @Valid @RequestBody command: SupersedeFeatureSchemaVersionCommand,
        request: HttpServletRequest
    ): CompletableFuture<SupersedeFeatureSchemaVersionCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
