package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;


@Command
data class MarkDataImportProcessingCommand(
    val dataImportJobId: UUID,
    val totalRows: Long,
    val status: String
) {

    @TargetEntityId
    val selection: DataImportJobSelection = DataImportJobSelection(dataImportJobId = dataImportJobId)



}
