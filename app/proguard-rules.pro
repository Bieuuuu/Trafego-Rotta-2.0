# ProGuard / R8 Rules para Trafego Rotta

# 1. Preservar modelos de dados e entidades
-keep class com.traffigorotta.model.** { *; }

# 2. Preservar serviços de acessibilidade e foreground service
-keep class com.traffigorotta.service.RottaAccessibilityService { *; }
-keep class com.traffigorotta.service.RottaOverlayService { *; }
-keep class com.traffigorotta.service.RottaFeedbackNotifier { *; }

# 3. Preservar ViewModels e ciclo de vida
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# 4. Jetpack Compose
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# 5. Room Database e Entidades Locais
-keep class com.traffigorotta.data.local.** { *; }
-keep class * extends androidx.room.RoomDatabase

# 6. Otimização sem quebrar empacotamento
-dontwarn java.lang.invoke.**
-dontwarn javax.annotation.**
