package tech.medo.dictionarymaintenance.updatedictionaryvaluetranslation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationSelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;


@Command
data class UpdateDictionaryValueTranslationCommand(
    val dictionaryValueTranslationId: UUID,
    val dictionaryValueId: UUID,
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode,
    val locale: LocaleCode,
    val displayName: String,
    val description: String?
) {
    @TargetEntityId
    val selection: DictionaryValueTranslationSelection = DictionaryValueTranslationSelection(dictionaryCode = dictionaryCode, valueCode = valueCode, locale = locale)


}
