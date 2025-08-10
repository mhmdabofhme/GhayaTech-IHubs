package ghayatech.ihubs.utils

// في الوحدة المشتركة (shared)
expect interface ImagePicker {
    suspend fun pickImage(): ByteArray?
}