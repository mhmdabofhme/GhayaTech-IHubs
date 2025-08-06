package ghayatech.ihubs.utils

interface Logger {
    fun debug(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable? = null)
}

expect fun getLogger(): Logger
