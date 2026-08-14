package tech.medo.fileupload.domain.states

enum class StagedFileStateEnum {
    STAGED,
    CONSUMED,
    DISCARDED,
    EXPIRED
}
