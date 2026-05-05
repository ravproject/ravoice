plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ravproject.ravoice.core.audio"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.core:core-ktx:1.12.0")
}
