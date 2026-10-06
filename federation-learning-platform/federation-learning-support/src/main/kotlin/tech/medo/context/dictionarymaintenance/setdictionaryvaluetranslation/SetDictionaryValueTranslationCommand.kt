package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationSelection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;

import tech.medo.dictionarymaintenance.dictionaryvaluetranslation.DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection

@Command
data class SetDictionaryValueTranslationCommand(
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


    val dictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection: DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection = DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection(normalizedDictionaryCode = dictionaryCode.value.trim().lowercase(), normalizedValueCode = valueCode.value.trim().lowercase(), normalizedLocale = locale.value.trim().lowercase())
}
