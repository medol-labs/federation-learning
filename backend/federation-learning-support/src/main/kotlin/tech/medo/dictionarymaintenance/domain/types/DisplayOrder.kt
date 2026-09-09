package tech.medo.dictionarymaintenance.domain.types

@JvmInline
value class DisplayOrder(val value: Int) {
    init {
        require(value >= 0 && value <= 999999) { "DisplayOrder violates range constraint" }
    }
}
