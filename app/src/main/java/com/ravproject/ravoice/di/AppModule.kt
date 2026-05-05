package com.ravproject.ravoice.di

import android.content.Context
import com.ravproject.ravoice.core.audio.AudioPlayerController
import com.ravproject.ravoice.core.audio.AudioRecorderController
import com.ravproject.ravoice.features.translator.SttEngineController
import com.ravproject.ravoice.features.voicechanger.VoiceChangerEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAudioRecorder() = AudioRecorderController()

    @Provides
    @Singleton
    fun provideAudioPlayer() = AudioPlayerController()

    @Provides
    @Singleton
    fun provideVoiceEngine() = VoiceChangerEngine()

    @Provides
    @Singleton
    fun provideSttEngine(@ApplicationContext context: Context) = SttEngineController(context)
}
