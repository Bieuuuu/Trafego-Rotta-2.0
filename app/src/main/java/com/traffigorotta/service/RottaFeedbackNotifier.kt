package com.traffigorotta.service

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.SignalGrade
import com.traffigorotta.model.TrafficLightConfig

/**
 * Notificador tátil e sonoro sutil para o motorista de aplicativo.
 *
 * Filosofia de Direção Segura:
 * - O motorista não pode desviar o olhar para a tela enquanto dirige.
 * - 1 toque firme no volante/bolso = "Vale" (Verde).
 * - 2 toques rápidos = "Neutra" (Amarelo).
 * - Sem vibração intrusiva = "Fraca" (Vermelho), evitando falsa expectativa.
 */
object RottaFeedbackNotifier {

    fun notifyOfferEvaluation(context: Context, evaluation: SignalEvaluation, config: TrafficLightConfig) {
        if (config.hapticFeedbackOnOffer) {
            triggerHapticFeedback(context, evaluation.grade)
        }

        if (config.soundChimeOnVale && evaluation.grade == SignalGrade.GOOD) {
            triggerDiscreetChime()
        }
    }

    private fun triggerHapticFeedback(context: Context, grade: SignalGrade) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                val defaultVibrator = vibratorManager?.defaultVibrator
                when (grade) {
                    SignalGrade.GOOD -> {
                        // 1 toque curto e firme (85ms)
                        val effect = VibrationEffect.createOneShot(85, VibrationEffect.DEFAULT_AMPLITUDE)
                        defaultVibrator?.vibrate(effect)
                    }
                    SignalGrade.MEDIUM -> {
                        // 2 toques rápidos (40ms vibra, 50ms pausa, 40ms vibra)
                        val timings = longArrayOf(0, 40, 50, 40)
                        val amplitudes = intArrayOf(0, 180, 0, 180)
                        val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)
                        defaultVibrator?.vibrate(effect)
                    }
                    SignalGrade.BAD -> {
                        // Não vibra para evitar distração desnecessária
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                when (grade) {
                    SignalGrade.GOOD -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator?.vibrate(VibrationEffect.createOneShot(85, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator?.vibrate(85)
                        }
                    }
                    SignalGrade.MEDIUM -> {
                        val pattern = longArrayOf(0, 40, 50, 40)
                        vibrator?.vibrate(pattern, -1)
                    }
                    SignalGrade.BAD -> {}
                }
            }
        } catch (_: Exception) {
            // Falha silenciosa defensiva
        }
    }

    private fun triggerDiscreetChime() {
        try {
            val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 60)
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 100)
        } catch (_: Exception) {
            // Falha silenciosa
        }
    }
}
