package tech.medo.dataexchange.dataexportjob

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
@RequestMapping("/dataexportjob")
class DataExportJobResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('request_data_export:execute')")
    @PostMapping("/requestdataexport")
    fun RequestDataExport(
        @Valid @RequestBody command: RequestDataExportCommand,
        request: HttpServletRequest
    ): CompletableFuture<RequestDataExportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('mark_data_export_processing:execute')")
    @PostMapping("/markdataexportprocessing")
    fun MarkDataExportProcessing(
        @Valid @RequestBody command: MarkDataExportProcessingCommand,
        request: HttpServletRequest
    ): CompletableFuture<MarkDataExportProcessingCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('complete_data_export:execute')")
    @PostMapping("/completedataexport")
    fun CompleteDataExport(
        @Valid @RequestBody command: CompleteDataExportCommand,
        request: HttpServletRequest
    ): CompletableFuture<CompleteDataExportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('fail_data_export:execute')")
    @PostMapping("/faildataexport")
    fun FailDataExport(
        @Valid @RequestBody command: FailDataExportCommand,
        request: HttpServletRequest
    ): CompletableFuture<FailDataExportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
