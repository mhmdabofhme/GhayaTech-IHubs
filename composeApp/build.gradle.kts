import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleGmsGoogleServices)
}


kotlin {


    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().all {
        binaries.all {
            // تقليل المعلومات القابلة للاستخراج في الإصدارات
            binaryOptions["stripDebugSymbols"] = "true"
            // لو احتجت الضبط عبر freeCompilerArgs:
            // freeCompilerArgs += listOf("-Xbinary=stripDebugInfoFromNativeLibs=true")
        }
    }

    jvm()
    
    sourceSets {

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.activity.compose)
            implementation(libs.firebase.messaging)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
//            implementation(libs.compose.ui.text)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.multiplatform.settings)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.bundles.ktor)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.kmp.date.time.picker)
            implementation(libs.kamel.image)

//            implementation(libs.clipboard.manager)

//            implementation(libs.image.loader)



        }

//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }
//        desktopMain.dependencies {
//            // Correct dependency for Desktop
//            implementation(compose.desktop.currentOs)
//            implementation(libs.sqldelight.driver.sqlite.native)
//        }
//        jvmMain.dependencies {
//            implementation(compose.desktop.currentOs)
////            implementation(libs.sqldelight.driver.sqlite.native)
//
////            implementation(libs.kotlinx.coroutinesSwing)
//        }
    }

    // تأكد من أن المستودعات موجودة هنا أيضاً
//    repositories {
//        google()
//        mavenCentral()
//    }
}

android {
    namespace = "ghayatech.ihubs"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ghayatech.ihubs"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file("keystore.jks") // ضع مسار ملف keystore هنا
//            storePassword = "كلمة_مرور_المخزن"
//            keyAlias = "اسم_المفتاح"
//            keyPassword = "كلمة_مرور_المفتاح"
//        }
//    }

    signingConfigs {
        create("release") {
            val storeFilePath = project.findProperty("RELEASE_STORE_FILE")?.toString()
            val storePassword = project.findProperty("RELEASE_STORE_PASSWORD")?.toString()
            val keyAlias = project.findProperty("RELEASE_KEY_ALIAS")?.toString()
            val keyPassword = project.findProperty("RELEASE_KEY_PASSWORD")?.toString()

            if (storeFilePath.isNullOrBlank() ||
                storePassword.isNullOrBlank() ||
                keyAlias.isNullOrBlank() ||
                keyPassword.isNullOrBlank()
            ) {
                throw GradleException("Release signing properties are missing. Define RELEASE_* in gradle.properties")
            }

            storeFile = file(storeFilePath)
            this.storePassword = storePassword
            this.keyAlias = keyAlias
            this.keyPassword = keyPassword
        }
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/*.kotlin_module",
                "META-INF/DEPENDENCIES",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true          // تشغيل R8
            isShrinkResources = true        // حذف الموارد غير المستخدمة
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
        }
//        debug {
//            // اختياري: تعطيل لوجات الطرف الثالث في debug حسب حاجتك
//            // isMinifyEnabled = false
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

//compose.desktop {
//    application {
//        mainClass = "ghayatech.ihubs.MainKt"
//
//        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//            packageName = "ghayatech.ihubs"
//            packageVersion = "1.0.0"
//        }
//    }
//}


