package com.ravproject.ravoice.features.translator

import kotlinx.coroutines.flow.*

class AudioPipeline(
    private val sttEngine: SttEngineController,
    private val translator: IndoNlpTranslator
) {
    /**
     * Memproses aliran audio dan menghasilkan aliran teks terjemahan.
     */
    fun startTranslationStream(
        audioSource: Flow<ByteArray>,
        fromLang: String,
        toLang: String
    ): Flow<String> = flow {
        audioSource.collect { chunk ->
            // 1. Ubah suara jadi teks (STT)
            val detectedText = sttEngine.processAudio(chunk)
            
            if (detectedText != null && detectedText.isNotEmpty()) {
                // 2. Terjemahkan teks tersebut
                val translatedText = translator.translate(detectedText, fromLang, toLang)
                
                // 3. Emit hasil ke UI
                emit(translatedText)
            }
        }
    }
}
