package com.ravproject.ravoice.features.translator

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class AudioPipelineTest {

    @Test
    fun testTranslationStream() = runBlocking {
        // Mocking dependencies
        val mockStt = mock(SttEngineController::class.java)
        val mockTranslator = mock(IndoNlpTranslator::class.java)
        val pipeline = AudioPipeline(mockStt, mockTranslator)

        val audioChunk = byteArrayOf(0, 1, 0, 1)
        
        `when`(mockStt.processAudio(audioChunk)).thenReturn("halo")
        `when`(mockTranslator.translate("halo", "id", "en")).thenReturn("hello")

        val result = pipeline.startTranslationStream(flowOf(audioChunk), "id", "en").first()
        
        assertEquals("hello", result)
    }
}
