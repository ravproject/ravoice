package com.ravproject.ravoice.features.voicechanger

class VoiceChangerEngine {
    companion object {
        init {
            System.loadLibrary("ravoice-dsp")
        }
    }

    external fun setPitch(pitch: Float)
    external fun processAudioChunk(input: ByteArray): ByteArray

    fun applyVoiceEffect(input: ByteArray): ByteArray {
        return processAudioChunk(input)
    }
}
