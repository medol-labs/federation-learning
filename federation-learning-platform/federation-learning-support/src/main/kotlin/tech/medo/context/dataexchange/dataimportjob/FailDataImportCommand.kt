package tech.medo.dataexchange.dataimportjob

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;


@Command
data class FailDataImportCommand(
    val dataImportJobId: UUID,
    val errorMessage: String,
    val status: String
) {

    @TargetEntityId
    val selection: DataImportJobSelection = DataImportJobSelection(dataImportJobId = dataImportJobId)



}
