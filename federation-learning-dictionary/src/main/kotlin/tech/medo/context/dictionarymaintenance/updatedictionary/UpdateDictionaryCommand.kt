package tech.medo.dictionarymaintenance.updatedictionary

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionary.DictionarySelection
import java.util.UUID;


@Command
data class UpdateDictionaryCommand(
    val dictionaryId: UUID,
    val dictionaryName: String,
    val description: String?
) {
    @TargetEntityId
    val selection: DictionarySelection = DictionarySelection(dictionaryId = dictionaryId)

}
