package com.ravproject.ravoice.data.repository

data class LanguageModel(
    val id: String,
    val name: String,
    val isLocal: Boolean, // True jika bahasa daerah (Sunda/Jawa)
    val modelUrl: String
)

interface LanguageRepository {
    fun getAvailableLanguages(): List<LanguageModel>
    fun isModelDownloaded(id: String): Boolean
}

class OfflineLanguageRepository : LanguageRepository {
    override fun getAvailableLanguages(): List<LanguageModel> {
        return listOf(
            LanguageModel("id", "Indonesian", false, ""),
            LanguageModel("su", "Sundanese", true, "url_to_sunda_model"),
            LanguageModel("jv", "Javanese", true, "url_to_jawa_model")
        )
    }

    override fun isModelDownloaded(id: String): Boolean {
        // Cek keberadaan folder model di internal storage
        return false
    }
}
