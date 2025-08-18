package ghayatech.ihubs.utils

// في shared/src/commonMain/kotlin/.../TokenManager.kt

// الواجهة أو الكلاس الذي سيتم تنفيذه لكل منصة
interface ITokenManager {
    fun getFCMToken(onTokenReceived: (String?) -> Unit)
}

// الكائن الذي سينشئ التنفيذ الصحيح
expect object TokenManagerFactory {
    fun createTokenManager(): ITokenManager
}