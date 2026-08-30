package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

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
@RequestMapping("/runtimedatasetmetadata")
class RecordRuntimeDatasetReprofiledMetadataResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('record_runtime_dataset_reprofiled_metadata:execute')")
    @PostMapping("/recordruntimedatasetreprofiledmetadata")
    fun RecordRuntimeDatasetReprofiledMetadata(
        @Valid @RequestBody command: RecordRuntimeDatasetReprofiledMetadataCommand,
        request: HttpServletRequest
    ): CompletableFuture<RecordRuntimeDatasetReprofiledMetadataCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
