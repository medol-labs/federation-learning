package tech.medo.dictionarymaintenance.dictionaryvaluetranslation

data class DictionaryValueTranslationDictionaryCodeValueCodeLocaleSelection(
    val normalizedDictionaryCode: String,
    val normalizedValueCode: String,
    val normalizedLocale: String
)

object DictionaryValueTranslationDictionaryCodeValueCodeLocaleReservationTags {
    const val DICTIONARY_CODE = "dictionaryCode"
    const val VALUE_CODE = "valueCode"
    const val LOCALE = "locale"
}
