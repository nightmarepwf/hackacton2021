buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://developer.huawei.com/repo/")
        maven(url = "https://oss.jfrog.org/libs-snapshot")
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${navigationVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")

        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
        classpath("com.huawei.agconnect:agcp:$agconnectVersion")
    }

    allprojects {
        repositories {
            google()
            mavenCentral()
            maven(url = "https://jitpack.io")
            maven(url = "https://plugins.gradle.org/m2/")
            maven(url = "https://developer.huawei.com/repo/")
            maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
            maven(url = "https://oss.jfrog.org/libs-snapshot")
            jcenter()
        }
    }

    if (!tasks.contains(task("clean"))) {
        println("registering clean task")
        tasks.register("clean", Delete::class) {
            delete(rootProject.buildDir)
        }
    }
    else
        println("clean task already registered")

    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class) {
            options.compilerArgs.addAll(listOf("-Xmaxerrs","500"))
        }
    }


}
