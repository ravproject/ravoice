package com.ravproject.ravoice.core.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack

class AudioPlayerController {
    private val sampleRate = 16000
    private val audioTrack = AudioTrack.Builder()
        .setAudioAttributes(AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build())
        .setAudioFormat(AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build())
        .setBufferSizeInBytes(AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT))
        .setTransferMode(AudioTrack.MODE_STREAM)
        .build()

    init {
        audioTrack.play()
    }

    fun play(data: ByteArray) {
        audioTrack.write(data, 0, data.size)
    }

    fun release() {
        audioTrack.stop()
        audioTrack.release()
    }
}
