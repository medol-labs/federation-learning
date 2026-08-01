package tech.medo.dictionarymaintenance.updatedictionary

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionary.DictionarySelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;


@Command
data class UpdateDictionaryCommand(
    val dictionaryId: UUID,
    val dictionaryName: String,
    val description: String?,
    val dictionaryCode: DictionaryCode
) {
    @TargetEntityId
    val selection: DictionarySelection = DictionarySelection(dictionaryCode = dictionaryCode)

}
