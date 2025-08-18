package ghayatech.ihubs.utils

import com.google.firebase.messaging.FirebaseMessaging


// التنفيذ الخاص بـ Android
class AndroidTokenManager : ITokenManager {
    override fun getFCMToken(onTokenReceived: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    onTokenReceived(null)
                    return@addOnCompleteListener
                }
                val token = task.result
                onTokenReceived(token)
            }
    }
}

// التنفيذ الفعلي لـ factory
actual object TokenManagerFactory {
    actual fun createTokenManager(): ITokenManager {
        return AndroidTokenManager()
    }
}