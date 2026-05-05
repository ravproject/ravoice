package com.ravproject.ravoice.core.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AudioRecorderController {
    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    @SuppressLint("MissingPermission")
    fun startRecording(): Flow<ByteArray> = flow {
        val recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        val buffer = ByteArray(bufferSize)
        recorder.startRecording()

        try {
            while (true) {
                val read = recorder.read(buffer, 0, buffer.size)
                if (read > 0) {
                    emit(buffer.copyOfRange(0, read))
                }
            }
        } finally {
            recorder.stop()
            recorder.release()
        }
    }.flowOn(Dispatchers.IO)
}
