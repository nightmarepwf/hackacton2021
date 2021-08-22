-android
-optimizationpasses 5
-repackageclasses ''
-allowaccessmodification
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-keepattributes *Annotation*
-keepattributes Exceptions

# InnerClasses is required to use Signature and EnclosingMethod is required to use InnerClasses
-keepattributes Signature,InnerClasses,EnclosingMethod
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# Producing useful obfuscated stack traces
-printmapping build/outputs/mapping/release/mapping.txt
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Change here com.qavan.app
-keep,includedescriptorclasses class com.qavan.app.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.qavan.app.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.qavan.app.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}


# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keep public class * extends java.lang.Exception

-keepclassmembers,allowshrinking,allowobfuscation interface * {
   keySize_ *;
}

-assumenosideeffects class kotlinx.coroutines.internal.MainDispatcherLoader {
     boolean FAST_SERVICE_LOADER_ENABLED return false;
 }

 -assumenosideeffects class kotlinx.coroutines.internal.FastServiceLoaderKt {
     boolean ANDROID_DETECTED return true;
 }

 -keep class kotlinx.coroutines.android.AndroidDispatcherFactory {*;}

 # Disable support for "Missing Main Dispatcher", since we always have Android main dispatcher
 -assumenosideeffects class kotlinx.coroutines.internal.MainDispatchersKt {
     boolean SUPPORT_MISSING return false;
 }

 -keep class com.google.crypto.** { *; }

 -keep class com.google.android.gms.** { *; }
 -keep class com.google.firebase.** { *; }

 -ignorewarnings
 -keep class com.huawei.agconnect.**{*;}

 -ignorewarnings
 -keep class com.hianalytics.android.**{*;}
 -keep class com.huawei.updatesdk.**{*;}
 -keep class com.huawei.hms.**{*;}