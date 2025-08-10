package ghayatech.ihubs.utils

actual interface ImagePicker {
    actual suspend fun pickImage(): ByteArray?
}