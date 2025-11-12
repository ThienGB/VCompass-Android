-keepattributes Signature
-keepattributes *Annotation*

-keep class com.google.gson.stream.** { *; }
-dontwarn sun.misc.**

# model folders
-keep class com.vcompass.domain.model.** { *; }
-keep class com.vcompass.core.model.** { *; }
-keep class com.example.vcompass.model.** { *; }
-keep class com.vcompass.data.model.** { *; }
-keep class com.vcompass.presentation.model.** { *; }

# ViewModel & SavedStateHandle
-keep class androidx.lifecycle.** { *; }
-keepnames class androidx.lifecycle.SavedStateHandle
-keep class **_KoinModule { *; }

-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-dontwarn androidx.compose.**