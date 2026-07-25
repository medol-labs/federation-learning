package tech.medo.dictionarymaintenance.archivedictionary

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionary.DictionarySelection
import java.util.UUID;


@Command
data class ArchiveDictionaryCommand(
    val dictionaryId: UUID,
    val archiveReason: String
) {
    @TargetEntityId
    val selection: DictionarySelection = DictionarySelection(dictionaryId = dictionaryId)

}
