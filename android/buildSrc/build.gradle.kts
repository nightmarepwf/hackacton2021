plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    maven(url = "https://jitpack.io")
    maven(url = "https://plugins.gradle.org/m2/")
    maven(url = "https://oss.jfrog.org/libs-snapshot")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
}