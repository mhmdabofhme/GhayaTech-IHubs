package ghayatech.ihubs.utils

// shared/src/iosMain/kotlin/.../TokenManager.kt

// التنفيذ الوهمي أو الفارغ لـ iOS
class IosTokenManager : ITokenManager {
    override fun getFCMToken(onTokenReceived: (String?) -> Unit) {
        // حاليًا لا يوجد تنفيذ لـ iOS
        onTokenReceived(null)
    }
}

// التنفيذ الفعلي لـ factory
actual object TokenManagerFactory {
    actual fun createTokenManager(): ITokenManager {
        return IosTokenManager()
    }
}