package com.ravproject.ravoice.features.translator

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TtsEngineController(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("id", "ID") // Default Bahasa Indonesia
            isReady = true
        }
    }

    fun speak(text: String) {
        if (isReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun setLanguage(locale: Locale) {
        tts.language = locale
    }

    fun release() {
        tts.stop()
        tts.shutdown()
    }
}
