# RAVOICE - Real-Time Voice Changer & Translator Overlay

**RAVOICE** adalah aplikasi Android canggih berbasis AI dan DSP (Digital Signal Processing) yang memungkinkan pengguna untuk mengubah karakteristik suara mereka secara real-time dan mendapatkan terjemahan ucapan langsung dalam bentuk floating overlay.

Aplikasi ini dirancang untuk bekerja secara transparan di atas aplikasi komunikasi lain seperti WhatsApp, Telegram, atau panggilan telepon reguler.

## 🚀 Fitur Utama

- **Real-Time Voice Changer**: Mengubah pitch, timbre, dan karakter suara Anda secara instan menggunakan engine native C++ (SoundTouch).
- **Floating Translation Overlay**: Menampilkan subtitle/transkrip terjemahan langsung di atas layar saat Anda sedang menelepon.
- **Multilingual Support**: Mendukung bahasa internasional dan bahasa daerah Indonesia (Sunda, Jawa, dll) secara offline.
- **100% On-Device Processing**: Tidak ada data suara yang dikirim ke server. Semua pemrosesan (STT, TTS, DSP) dilakukan di perangkat Anda.
- **Modular Architecture**: Dibangun dengan Clean Architecture dan Gradle Multi-Module untuk skalabilitas tinggi.

## 🛠️ Stack Teknologi

- **UI**: Jetpack Compose (Modern Declarative UI with Glassmorphism)
- **Audio Processing**: NDK/JNI (C++), SoundTouch Library
- **Speech-to-Text (STT)**: Vosk Engine (Offline)
- **Translator**: IndoNLP Pipeline & Argos Translate
- **Dependency Injection**: Dagger Hilt
- **Concurrency**: Kotlin Coroutines & Flow

## 📂 Struktur Proyek

- `:app` - Entry point, Permission handling, dan Hilt Application.
- `:core:audio` - Mesin perekaman, pemutaran, dan JNI DSP bridge.
- `:features:translator` - Logika STT, Translation Pipeline, dan TTS.
- `:features:overlay` - Service untuk mengelola jendela melayang (Floating Window).
- `:core:designsystem` - Komponen UI Compose premium.
- `:data:repository` - Manajemen model bahasa offline.

## 🏗️ Cara Build

Aplikasi ini menggunakan **GitHub Actions** untuk otomatisasi build. Namun, jika Anda ingin mencoba secara manual:

1. Clone repositori ini.
2. Letakkan model Vosk (Indonesian) di `app/src/main/assets/model`.
3. Letakkan source code SoundTouch di `core/audio/src/main/cpp/soundtouch`.
4. Jalankan perintah:
   ```bash
   ./gradlew assembleDebug
   ```

## 📝 Catatan Penting

- **Audio Injection**: Pada perangkat Android non-root, aplikasi menggunakan `VOICE_COMMUNICATION` stream untuk mencoba mengirim audio ke aplikasi lain. Hasil dapat bervariasi tergantung pada versi OS dan batasan vendor.
- **Permissions**: Aplikasi membutuhkan izin `Overlay`, `Record Audio`, dan `Accessibility Service` agar fitur subtitle real-time dapat bekerja maksimal.

---
Dikembangkan dengan ❤️ oleh **ravproject**.
