package tech.medo.dictionarymaintenance.domain

object Concepts {
    data object Dictionary {
        const val NAME = "Dictionary"
        val slices = listOf("RegisterDictionary", "UpdateDictionary", "ArchiveDictionary", "DictionaryCatalog")
        val states = listOf("Registered", "Archived")
    }

    data object DictionaryValue {
        const val NAME = "DictionaryValue"
        val slices = listOf("AddDictionaryValue", "DisableDictionaryValue", "EnableDictionaryValue", "DictionaryValueCatalog")
        val states = listOf("Active", "Disabled")
    }
}
