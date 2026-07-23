package tech.medo.dictionarymaintenance.adddictionaryvalue

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueSelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.DisplayOrder;


@Command
data class AddDictionaryValueCommand(
    val dictionaryValueId: UUID = java.util.UUID.randomUUID(),
    val dictionaryId: UUID,
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode,
    val displayName: String,
    val displayOrder: DisplayOrder?,
    val description: String?,
    val active: Boolean
) {
    @TargetEntityId
    val selection: DictionaryValueSelection = DictionaryValueSelection(dictionaryCode = dictionaryCode, valueCode = valueCode)

}
