package tech.medo.dictionarymaintenance.registerdictionary

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionary.DictionarySelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;

import tech.medo.dictionarymaintenance.dictionary.DictionaryCodeSelection

@Command
data class RegisterDictionaryCommand(
    val dictionaryId: UUID = java.util.UUID.randomUUID(),
    val dictionaryCode: DictionaryCode,
    val dictionaryName: String,
    val description: String?
) {
    @TargetEntityId
    val selection: DictionarySelection = DictionarySelection(dictionaryCode = dictionaryCode)

    val dictionaryCodeSelection: DictionaryCodeSelection = DictionaryCodeSelection(normalizedName = dictionaryCode.value.trim().lowercase())
}
