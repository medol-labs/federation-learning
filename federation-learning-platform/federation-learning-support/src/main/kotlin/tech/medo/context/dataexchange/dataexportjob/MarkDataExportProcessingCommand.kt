package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;


@Command
data class MarkDataExportProcessingCommand(
    val dataExportJobId: UUID,
    val status: String
) {
    @TargetEntityId
    val selection: DataExportJobSelection = DataExportJobSelection(dataExportJobId = dataExportJobId)


}
