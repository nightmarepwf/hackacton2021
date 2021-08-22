const val kotlinVersion = "1.5.21"
const val composeVersion = "1.0.1"
const val roomVersion = "2.3.0"
const val ktorVersion = "1.6.2"
const val navigationVersion = "2.4.0-alpha06"
const val workVersion = "2.7.0-alpha05"
const val hiltVersion = "2.38.1"
const val coilVersion = "1.3.2"
const val firebaseVersion = "28.3.1"
const val agconnectVersion = "1.6.0.300"

object Dependencies {

    object Kotlin {
        private const val kotlinStdlibJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        private const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"

        val impl = listOf(
            kotlinStdlibJdk,
            kotlinReflect,
        )
    }

    object KotlinX {
        private const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.1"
        private const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"
        private const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.4"

        val impl = listOf(
            kotlinxDatetime,
            kotlinxCoroutines,
            kotlinxCollectionsImmutable,
        )
    }

    object Android {
        private const val annotation = "androidx.annotation:annotation:1.2.0"
        private const val core = "androidx.core:core-ktx:1.6.0"
        private const val activity = "androidx.activity:activity-ktx:1.3.1"
        private const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        private const val fragment = "androidx.fragment:fragment-ktx:1.4.0-alpha06"
        private const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        private const val appcompatResources = "androidx.appcompat:appcompat-resources:1.3.1"
        private const val material = "com.google.android.material:material:1.4.0"
        private const val preference = "androidx.preference:preference-ktx:1.1.1"

        val impl = listOf(
            annotation,
            core,
            activity,
            activityCompose,
            fragment,
            appcompat,
            appcompatResources,
            material,
            preference,
        )
    }

    object Dev {
        private const val timber = "com.jakewharton.timber:timber:5.0.1"
        private const val notificationDslCore = "com.kirich1409.android-notification-dsl:core:0.1.0"
        private const val notificationDslExtensions = "com.kirich1409.android-notification-dsl:extensions:0.1.0"
        private const val notificationDslMedia = "com.kirich1409.android-notification-dsl:media:0.1.0"
        private const val ae = "com.tunjid.androidx:core:1.3.2"
        private const val prefetchViewPoolCore = "com.github.vivid-money.prefetchviewpool:prefetchviewpool-core:1.0.0"
        private const val prefetchViewPoolCoroutines = "com.github.vivid-money.prefetchviewpool:prefetchviewpool-coroutines:1.0.0"
        private const val gson = "com.google.code.gson:gson:2.8.8"

        val impl = listOf(
            timber,
            notificationDslCore,
            notificationDslExtensions,
            notificationDslMedia,
            ae,
            prefetchViewPoolCore,
            prefetchViewPoolCoroutines,
            gson,
        )
    }

    object Views {
        private const val vectordrawable = "androidx.vectordrawable:vectordrawable:1.1.0"
        private const val vectordrawableAnimated = "androidx.vectordrawable:vectordrawable-animated:1.1.0"
        private const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.0"
        private const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        private const val recyclerviewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"
        private const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        private const val cardview = "androidx.cardview:cardview:1.0.0"

        val impl = listOf(
            vectordrawable,
            vectordrawableAnimated,
            constraintlayout,
            recyclerview,
            recyclerviewSelection,
            viewpager2,
            cardview,
        )
    }

    object Hilt {
        private const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        private const val hiltWork = "androidx.hilt:hilt-work:1.0.0"
        private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        private const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"

        val impl = listOf(
            hiltAndroid,
            hiltWork,
        )
        val kapt = listOf(
            hiltAndroidCompiler,
            hiltCompiler,
        )
    }

    object Lifecycle {
        private const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        private const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
        private const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        private const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"

        val impl = listOf(
            lifecycleExtensions,
            lifecycleCommonJava8,
            lifecycleRuntimeKtx,
            lifecycleViewModelKtx,
        )
    }

    object Room {
        private const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        private const val roomKtx = "androidx.room:room-ktx:$roomVersion"
        private const val roomKapt = "androidx.room:room-compiler:$roomVersion"

        val impl = listOf(
            roomRuntime,
            roomKtx,
        )
        val kapt = listOf(
            roomKapt,
        )
    }

    object Work {
        private const val workRuntimeKtx = "androidx.work:work-runtime-ktx:$workVersion"
        private const val workGcm = "androidx.work:work-gcm:$workVersion"

        val impl = listOf(
            workRuntimeKtx,
            workGcm,
        )
    }

    object Ktor {
        private const val ktorOkHttp = "io.ktor:ktor-client-okhttp:$ktorVersion"
        private const val ktorAndroid = "io.ktor:ktor-client-android:$ktorVersion"
        private const val ktorJson = "io.ktor:ktor-client-json:$ktorVersion"
        private const val ktorSerializationJvm = "io.ktor:ktor-client-serialization-jvm:$ktorVersion"
        private const val ktorLoggingJvm = "io.ktor:ktor-client-logging-jvm:$ktorVersion"

        val impl = listOf(
            ktorOkHttp,
            ktorAndroid,
            ktorJson,
            ktorSerializationJvm,
            ktorLoggingJvm,
        )
    }

    object Navigation {
        private const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        private const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        private const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:$navigationVersion"
        private const val navigationCompose = "androidx.navigation:navigation-compose:$navigationVersion"

        val impl = listOf(
            navigationUiKtx,
            navigationFragmentKtx,
            navigationRuntimeKtx,
            navigationCompose,
        )
    }

    object Facebook {
        private const val fresco = "com.facebook.fresco:fresco:2.5.0"
        private const val frescoAnimatedGif = "com.facebook.fresco:animated-gif:2.5.0"
        private const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

        val impl = listOf(
            fresco,
            frescoAnimatedGif,
            shimmer,
        )
    }

    object Coil {
        private const val coil = "io.coil-kt:coil:1.3.2"
        private const val coilSvg = "io.coil-kt:coil-svg:$coilVersion"
        private const val coilCompose = "io.coil-kt:coil-compose:$coilVersion"

        val impl = listOf(
            coil,
            coilSvg,
            coilCompose,
        )
    }

    object Compose {
        private const val accompanistVersion = "0.17.0"

        private const val compiler = "androidx.compose.compiler:compiler:$composeVersion"
        private const val ui = "androidx.compose.ui:ui:$composeVersion"
        private const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        private const val animation = "androidx.compose.animation:animation:$composeVersion"
        private const val animationCore = "androidx.compose.animation:animation-core:$composeVersion"
        private const val material = "androidx.compose.material:material:$composeVersion"
        private const val materialIconsCore = "androidx.compose.material:material-icons-core:$composeVersion"
        private const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
        private const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
        private const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
        private const val foundationLayout = "androidx.compose.foundation:foundation-layout:$composeVersion"
        private const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
        private const val paging = "androidx.paging:paging-compose:1.0.0-alpha12"
        private const val accompanistFlowLayout = "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
        private const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
        private const val accompanistPager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
        private const val accompanistPagerIndicator = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
        private const val accompanistInsets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
        private const val accompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:$accompanistVersion"

        val impl = listOf(
            compiler,
            ui,
            uiTooling,
            animation,
            animationCore,
            material,
            materialIconsCore,
            materialIconsExtended,
            runtime,
            foundation,
            foundationLayout,
            constraintLayout,
            paging,
            accompanistFlowLayout,
            accompanistNavigationAnimation,
            accompanistPager,
            accompanistPagerIndicator,
            accompanistInsets,
            accompanistInsetsUi,
        )
        val debugImpl = listOf(
            uiTooling,
        )
    }

    object Gms {
        val impl = listOf(
            "com.google.firebase:firebase-crashlytics-ndk",
            "com.google.firebase:firebase-messaging-ktx",
        )
    }

    object Hms {
        val impl = listOf(
            "com.huawei.agconnect:agconnect-crash:$agconnectVersion",
            "com.huawei.hms:push:5.3.0.304",
        )
    }

}

