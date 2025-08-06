package ghayatech.ihubs.networking.network

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ghayatech.ihubs.utils.Constants

//


//expect fun getHttpClient(): HttpClient
//
//fun createHttpClient(): HttpClient {
//    return HttpClient(getHttpClientEngine()) {
//        install(ContentNegotiation) {
//            json(Json {
//                ignoreUnknownKeys = true
//                prettyPrint = true
//            })
//        }
//    }
//}
//
//expect fun getHttpClientEngine(): HttpClientEngineFactory<*>

//
fun createHttpClient(engine: HttpClientEngine,settings:Settings): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        defaultRequest {
            header(HttpHeaders.Accept, ContentType.Application.Json)
            val token = settings.getString(Constants.TOKEN,"")
            if (token.isNotEmpty()) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}

// commonMain
expect fun createPlatformHttpClient(settings: Settings): HttpClient
