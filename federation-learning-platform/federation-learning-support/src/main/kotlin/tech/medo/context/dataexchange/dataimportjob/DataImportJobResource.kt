package tech.medo.dataexchange.dataimportjob

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
@RequestMapping("/dataimportjob")
class DataImportJobResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('request_data_import:execute')")
    @PostMapping("/requestdataimport")
    fun RequestDataImport(
        @Valid @RequestBody command: RequestDataImportCommand,
        request: HttpServletRequest
    ): CompletableFuture<RequestDataImportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('mark_data_import_processing:execute')")
    @PostMapping("/markdataimportprocessing")
    fun MarkDataImportProcessing(
        @Valid @RequestBody command: MarkDataImportProcessingCommand,
        request: HttpServletRequest
    ): CompletableFuture<MarkDataImportProcessingCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('complete_data_import:execute')")
    @PostMapping("/completedataimport")
    fun CompleteDataImport(
        @Valid @RequestBody command: CompleteDataImportCommand,
        request: HttpServletRequest
    ): CompletableFuture<CompleteDataImportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }

    @PreAuthorize("hasAuthority('*:*') or hasAuthority('fail_data_import:execute')")
    @PostMapping("/faildataimport")
    fun FailDataImport(
        @Valid @RequestBody command: FailDataImportCommand,
        request: HttpServletRequest
    ): CompletableFuture<FailDataImportCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
