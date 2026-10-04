package tech.medo.fileupload.domain.states

enum class UploadedFileStateEnum {
    Available,
    Referenced,
    Discarded,
    Expired
}
