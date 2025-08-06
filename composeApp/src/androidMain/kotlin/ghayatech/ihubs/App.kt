package ghayatech.ihubs

import android.app.Application
import ghayatech.ihubs.utils.androidModule
import ghayatech.ihubs.utils.appModule
import io.ktor.http.ContentType
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin



class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // قم بتهيئة Koin هنا، وسيتم استدعاؤه مرة واحدة فقط عند بدء تشغيل التطبيق
        startKoin {
            androidContext(this@App)
            modules(
                androidModule,
                appModule
            )
        }
//        startKoin {
//            modules(appModule)
//
//            // ستقوم على الأرجح بإضافة وحدات Koin الخاصة بك هنا
//            // على سبيل المثال: modules(appModule)
//            // logFile("koin_logs.txt") // اختياري: إذا كنت تريد سجلات Koin
//            // androidLogger() // اختياري: لتسجيل خاص بأندرويد
//            // androidContext(this@App) // مطلوب لسياق أندرويد
//        }
    }
}