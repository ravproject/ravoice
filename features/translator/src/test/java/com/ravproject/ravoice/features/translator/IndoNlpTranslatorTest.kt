package com.ravproject.ravoice.features.translator

import org.junit.Assert.assertEquals
import org.junit.Test

class IndoNlpTranslatorTest {

    private val translator = IndoNlpTranslator()

    @Test
    fun testSundaToIndoTranslation() {
        val input = "wilujeng enjing"
        val expected = "selamat enjing" // Sesuai placeholder logic kita
        val result = translator.translate(input, "su", "id")
        assertEquals(expected, result)
    }

    @Test
    fun testIndoToGlobalTranslation() {
        val input = "halo"
        val expected = "Translated: halo"
        val result = translator.translate(input, "id", "en")
        assertEquals(expected, result)
    }
}
