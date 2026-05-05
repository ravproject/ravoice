package com.ravproject.ravoice.core.audio

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.ravproject.ravoice.features.translator.AudioPipeline
import com.ravproject.ravoice.features.translator.SttEngineController
import com.ravproject.ravoice.features.translator.TtsEngineController
import com.ravproject.ravoice.features.translator.IndoNlpTranslator
import com.ravproject.ravoice.features.voicechanger.VoiceChangerEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class AudioStreamingService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    @Inject lateinit var recorder: AudioRecorderController
    @Inject lateinit var player: AudioPlayerController
    @Inject lateinit var voiceEngine: VoiceChangerEngine
    @Inject lateinit var sttEngine: SttEngineController
    
    private lateinit var ttsEngine: TtsEngineController
    private lateinit var translator: IndoNlpTranslator
    private lateinit var pipeline: AudioPipeline

    private var isRunning = false

    override fun onCreate() {
        super.onCreate()
        ttsEngine = TtsEngineController(this)
        translator = IndoNlpTranslator()
        pipeline = AudioPipeline(sttEngine, translator)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        if (!isRunning) startFullPipeline()
        return START_STICKY
    }

    private fun startFullPipeline() {
        isRunning = true
        val audioFlow = recorder.startRecording()

        // 1. Voice Changer Path (Direct Loop)
        serviceScope.launch {
            audioFlow.collect { chunk ->
                val modified = voiceEngine.applyVoiceEffect(chunk)
                player.play(modified)
            }
        }

        // 2. Translation Path (Speech-to-Text -> Translate -> TTS)
        serviceScope.launch {
            pipeline.startTranslationStream(audioFlow, "id", "en").collect { translatedText ->
                // Emit to UI and speak out
                ttsEngine.speak(translatedText)
                // broadcastResultToOverlay(translatedText)
            }
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, "ravoice_stream")
        .setContentTitle("RAVOICE Production Engine")
        .setContentText("Voice Changer & Translator Active")
        .setSmallIcon(android.R.drawable.ic_btn_speak_now)
        .build()

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        serviceScope.cancel()
        ttsEngine.release()
        player.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
