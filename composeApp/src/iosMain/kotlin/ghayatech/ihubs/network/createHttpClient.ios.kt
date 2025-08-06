package ghayatech.ihubs.networking.network

// iosMain
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.*
import io.ktor.client.*

actual fun createPlatformHttpClient(settings: Settings): HttpClient {
    return createHttpClient(Darwin.create(),settings)
}