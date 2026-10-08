package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class RequestDataImportCommand(
    val dataImportJobId: UUID,
    val commandName: String,
    val resourceName: String,
    val originalFileName: String,
    val filePath: String,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime,
    val requestHash: String,
    val status: String
) {
    @TargetEntityId
    val selection: DataImportJobSelection = DataImportJobSelection(dataImportJobId = dataImportJobId)


}
