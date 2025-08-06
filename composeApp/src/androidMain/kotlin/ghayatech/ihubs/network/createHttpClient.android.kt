package ghayatech.ihubs.networking.network

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun createPlatformHttpClient(settings: Settings): HttpClient {
    return createHttpClient(OkHttp.create(),settings)
}