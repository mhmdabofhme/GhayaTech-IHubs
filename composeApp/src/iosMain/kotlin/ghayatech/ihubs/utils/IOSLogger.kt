package ghayatech.ihubs.utils

import platform.Foundation.NSLog

class IOSLogger : Logger {
    override fun debug(tag: String, message: String) {
        NSLog("DEBUG [$tag]: $message")
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        NSLog("ERROR [$tag]: $message\n${throwable?.message ?: ""}")
    }
}

actual fun getLogger(): Logger = IOSLogger()
