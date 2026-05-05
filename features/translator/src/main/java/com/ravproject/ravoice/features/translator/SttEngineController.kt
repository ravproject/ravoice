package com.ravproject.ravoice.features.translator

import android.content.Context
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.StorageService
import javax.inject.Inject

class SttEngineController(private val context: Context) {
    private var model: Model? = null
    private var recognizer: Recognizer? = null

    /**
     * Load model Vosk dari storage.
     */
    fun initModel(modelPath: String, onReady: () -> Unit) {
        // Logika unpack model dari assets jika belum ada di storage
        StorageService.unpack(context, "model-id-indonesian", "model",
            { m ->
                model = m
                recognizer = Recognizer(m, 16000.0f)
                onReady()
            },
            { e -> e.printStackTrace() }
        )
    }

    /**
     * Memproses chunk audio dan mengembalikan teks jika kalimat selesai.
     */
    fun processAudio(data: ByteArray): String? {
        if (recognizer?.acceptWaveform(data, data.size) == true) {
            val result = recognizer?.result ?: ""
            return extractText(result)
        }
        return null
    }

    private fun extractText(json: String): String {
        // Parsing sederhana hasil JSON Vosk: {"text": "halo dunia"}
        return json.substringAfter("\"text\" : \"").substringBefore("\"")
    }
}
