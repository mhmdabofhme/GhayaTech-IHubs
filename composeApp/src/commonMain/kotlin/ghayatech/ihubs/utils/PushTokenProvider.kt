package ghayatech.ihubs.utils

interface PushTokenProvider {
    suspend fun getPushToken(): String?
}
