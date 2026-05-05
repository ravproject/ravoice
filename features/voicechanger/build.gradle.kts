plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ravproject.ravoice.features.voicechanger"
    compileSdk = 34
}

dependencies {
    implementation(project(":core:audio"))
}
