# --- Compose: حافظ على دوال @Composable و @Preview
-keepclasseswithmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-keepclasseswithmembers class * {
    @androidx.compose.ui.tooling.preview.Preview <methods>;
}

# Compose runtime internals
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Kotlin coroutines
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

# Koin (تجنّب NoClassDefFoundError في الإصدار المضلل)
-keep class org.koin.** { *; }
-keep class * implements org.koin.core.component.KoinComponent { *; }
-keepclassmembers class ** {
    @org.koin.core.annotation.* <fields>;
    @org.koin.core.annotation.* <methods>;
}

# kotlinx.serialization (احتفظ بالمولّدات)
-keep,allowobfuscation,allowoptimization class kotlinx.serialization.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# Ktor + OkHttp (على أندرويد)
-dontwarn io.ktor.**
-keep class io.ktor.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-dontwarn okio.**
-keep class okio.** { *; }

# Moshi (إن كنت تستخدمه مع Ktor/OkHttp)
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**

# Gson (إن وجد)
-keep class com.google.gson.stream.** { *; }

# إزالة جميع استدعاءات Log في الإصدار (اختياري لكن مفيد)
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** e(...);
}

# منع التحذيرات العامة
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.annotation.**
