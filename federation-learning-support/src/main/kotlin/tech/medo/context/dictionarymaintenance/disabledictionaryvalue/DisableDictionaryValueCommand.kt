package tech.medo.dictionarymaintenance.disabledictionaryvalue

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionaryvalue.DictionaryValueSelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;


@Command
data class DisableDictionaryValueCommand(
    val dictionaryValueId: UUID,
    val disabledReason: String,
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode
) {
    @TargetEntityId
    val selection: DictionaryValueSelection = DictionaryValueSelection(dictionaryCode = dictionaryCode, valueCode = valueCode)

}
