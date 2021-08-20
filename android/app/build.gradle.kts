import com.android.build.gradle.internal.cxx.logging.errorln
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val versionMajor = App.major
val versionMinor = App.minor
val versionPatch = App.patch
val versionBuild = App.build

val propertiesFileName = "secrets.properties"
val keystoreFileName = "keystore.jks"
val project = "Poehali"

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.qavan.app"

        buildToolsVersion = "30.0.3"
        minSdk = 21
        targetSdk = 30

        versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "${projectDir}/schemas"
                arguments["room.incremental"] = "true"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        languageVersion = kotlinVersion.split(".").subList(0,2).joinToString(separator = ".")
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    kapt {
        correctErrorTypes = true
        arguments {
            arg("dagger.fastInit","enabled")
            arg("dagger.formatGeneratedSource","disabled")
        }
    }

    sourceSets {
        create(Services.gms) {
            java.srcDirs("src/main/kotlin", "src/${Services.gms}/kotlin")
        }
        create(Services.hms) {
            java.srcDirs("src/main/kotlin", "src/${Services.hms}/kotlin")
        }
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    val secretProperties = Properties()
    secretProperties.load(rootProject.file(propertiesFileName).inputStream())

    if (rootProject.file(propertiesFileName).exists()) {
        println("$propertiesFileName found")
        signingConfigs {
            create("upload") {
                storeFile = file("..\\$keystoreFileName")
                storePassword = "${secretProperties["signing_keystore_password"]}"
                keyPassword = "${secretProperties["signing_key_password"]}"
                keyAlias = "${secretProperties["signing_key_alias"]}"
            }
        }
    }
    else {
        errorln("$propertiesFileName not found")
    }

    bundle {
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
        language {
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("upload")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
            buildConfigField("String", "PROJECT", "\"$project.DEV\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
//            configure<com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension> {
//                nativeSymbolUploadEnabled = true
//                unstrippedNativeLibsDir = "build/intermediates/merged_native_libs/masterCertificateRelease/out/lib/"
//            }
            signingConfig = signingConfigs.getByName("upload")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro"),
                file("proguard-rules-coroutines.pro")
            )
            ndk.debugSymbolLevel = "FULL"
            buildConfigField("String", "PROJECT", "\"$project\"")
        }
    }

    flavorDimensions += mutableListOf("services", "version")

    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resourceConfigurations += mutableSetOf("com", "xxhdpi")
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
        }
        create("master") {
            dimension = "version"
        }
        create(Services.gms) {
            dimension = "services"
        }
        create(Services.hms) {
            dimension = "services"
//            applicationIdSuffix = Services.hms
        }
    }

    dependencies {
        implementation(files(fileTree("libs")))
        //kotlin and kotlinx dependencies
        Dependencies.Kotlin.impl.forEach { dep -> implementation(dep) }
        Dependencies.KotlinX.impl.forEach { dep -> implementation(dep) }
        //android dependencies
        Dependencies.Android.impl.forEach { dep -> implementation(dep) }
        //lifecycle dependencies
        Dependencies.Lifecycle.impl.forEach { dep -> implementation(dep) }
        //navigation dependencies
        Dependencies.Navigation.impl.forEach { dep -> implementation(dep) }
        //views dependencies
        Dependencies.Views.impl.forEach { dep -> implementation(dep) }
        //facebook dependencies
        Dependencies.Facebook.impl.forEach { dep -> implementation(dep) }
        //coil dependencies
        Dependencies.Coil.impl.forEach { dep -> implementation(dep) }
        //compose dependencies
        Dependencies.Compose.impl.forEach { dep -> implementation(dep) }
        Dependencies.Compose.debugImpl.forEach { dep -> debugImplementation(dep) }
        //room dependencies
        Dependencies.Room.impl.forEach { dep -> implementation(dep) }
        Dependencies.Room.kapt.forEach { dep -> kapt(dep) }
        //ktor dependencies
        Dependencies.Ktor.impl.forEach { dep -> implementation(dep) }
        //work dependencies
        Dependencies.Work.impl.forEach { dep -> implementation(dep) }
        //hilt dependencies
        Dependencies.Hilt.impl.forEach { dep -> implementation(dep) }
        Dependencies.Hilt.kapt.forEach { dep -> kapt(dep) }
        //dev dependencies
        Dependencies.Dev.impl.forEach { dep -> implementation(dep) }
        //        implementation("com.squareup.leakcanary:leakcanary-android:2.6")
        //region gms hms
        "${Services.gms}Implementation"(platform("com.google.firebase:firebase-bom:$firebaseVersion"))
        Dependencies.Gms.impl.forEach { impl -> "${Services.gms}Implementation"(impl) }
        Dependencies.Hms.impl.forEach { impl -> "${Services.hms}Implementation"(impl) }
        //endregion

    }


}

private val startParameterTaskRequests = gradle.startParameter.taskRequests.toString().toLowerCase()
private val isGms = startParameterTaskRequests.contains(Services.gms)
private val isHms = startParameterTaskRequests.contains(Services.hms)
when {
    isGms -> {
        errorln("Applying ${Services.gms.toUpperCase()} plugins")
        apply(plugin = "com.google.gms.google-services")
        apply(plugin = "com.google.firebase.crashlytics")
    }
    isHms -> {
        errorln("Applying ${Services.hms.toUpperCase()} plugins")
        apply(plugin = "com.huawei.agconnect")
    }
}