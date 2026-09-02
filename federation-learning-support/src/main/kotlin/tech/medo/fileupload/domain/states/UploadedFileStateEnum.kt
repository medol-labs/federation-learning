package tech.medo.fileupload.domain.states

enum class UploadedFileStateEnum {
    AVAILABLE,
    REFERENCED,
    DISCARDED,
    EXPIRED
}
