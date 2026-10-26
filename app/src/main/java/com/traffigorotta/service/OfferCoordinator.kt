package com.traffigorotta.service

import com.traffigorotta.data.FinancialRepository
import com.traffigorotta.data.JourneyRepository
import com.traffigorotta.data.RideRepository
import com.traffigorotta.model.EarningSource
import com.traffigorotta.model.EntryType
import com.traffigorotta.model.FinancialEntry
import com.traffigorotta.model.RideOffer
import com.traffigorotta.model.RideRecord
import com.traffigorotta.model.RideStatus
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.SignalGrade
import com.traffigorotta.model.TrafficLightConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

/**
 * Coordenador central em memória para comunicação reativa entre:
 * - RottaAccessibilityService (somente leitura de ofertas e corridas)
 * - RottaOverlayService (foreground service da bolha de 52px e painel expandido)
 * - Repositórios de dados locais Room (relatórios automáticos e metas)
 */
object OfferCoordinator {

    private val _config = MutableStateFlow(TrafficLightConfig())
    val config: StateFlow<TrafficLightConfig> = _config.asStateFlow()

    private val _latestOffer = MutableStateFlow<RideOffer?>(null)
    val latestOffer: StateFlow<RideOffer?> = _latestOffer.asStateFlow()

    private val _latestEvaluation = MutableStateFlow<SignalEvaluation?>(null)
    val latestEvaluation: StateFlow<SignalEvaluation?> = _latestEvaluation.asStateFlow()

    private val _journeyEarnings = MutableStateFlow(184.20)
    val journeyEarnings: StateFlow<Double> = _journeyEarnings.asStateFlow()

    private val _isOverlayExpanded = MutableStateFlow(false)
    val isOverlayExpanded: StateFlow<Boolean> = _isOverlayExpanded.asStateFlow()

    // Estado do ciclo de vida da corrida ativa (Fase 2)
    private val _activeRideStatus = MutableStateFlow<RideEventType?>(null)
    val activeRideStatus: StateFlow<RideEventType?> = _activeRideStatus.asStateFlow()

    private val _lastCompletedRideFare = MutableStateFlow<Double?>(null)
    val lastCompletedRideFare: StateFlow<Double?> = _lastCompletedRideFare.asStateFlow()

    private val _autoReportNotice = MutableStateFlow<String?>(null)
    val autoReportNotice: StateFlow<String?> = _autoReportNotice.asStateFlow()

    // Instâncias opcionais compartilhadas de repositório para persistência automática
    var journeyRepository: JourneyRepository? = null
    var financialRepository: FinancialRepository? = null
    var rideRepository: RideRepository? = null

    fun updateConfig(newConfig: TrafficLightConfig) {
        _config.value = newConfig
        _latestOffer.value?.let { offer ->
            _latestEvaluation.value = SignalEvaluation.evaluate(offer, newConfig)
        }
    }

    /**
     * Disparado quando o AccessibilityService lê uma nova oferta de corrida na tela.
     */
    fun onNewOfferReceived(offer: RideOffer) {
        _latestOffer.value = offer
        val eval = SignalEvaluation.evaluate(offer, _config.value)
        _latestEvaluation.value = eval
        _activeRideStatus.value = RideEventType.OFFER

        if (_config.value.autoExpandOnOffer) {
            _isOverlayExpanded.value = true
        }
    }

    /**
     * Disparado quando o motorista aceita a corrida no app parceiro.
     */
    fun onRideAccepted(event: ParsedRideEvent) {
        _activeRideStatus.value = RideEventType.ACCEPTED
        // Se a bolha estava expandida com a oferta, recolhe para não cobrir navegação do motorista
        _isOverlayExpanded.value = false
    }

    /**
     * Disparado quando a viagem é concluída nos apps Uber ou 99 (Relatório Automático).
     * Não exige que o motorista digite nada ao volante!
     */
    fun onRideCompleted(
        event: ParsedRideEvent,
        customJourneyRepo: JourneyRepository? = journeyRepository,
        customFinancialRepo: FinancialRepository? = financialRepository,
        customRideRepo: RideRepository? = rideRepository
    ) {
        val earnedAmount = event.grossValue
        val distance = event.distanceKm
        val minutes = event.durationMinutes

        _activeRideStatus.value = RideEventType.COMPLETED
        _lastCompletedRideFare.value = earnedAmount
        _journeyEarnings.value += earnedAmount

        val source = if (event.sourceApp.contains("99", ignoreCase = true)) {
            EarningSource.APP_99
        } else {
            EarningSource.UBER
        }

        // 1. Grava no repositório de corridas
        val grade = _latestEvaluation.value?.grade ?: SignalGrade.GOOD
        val rideRecord = RideRecord(
            id = "ride-${System.currentTimeMillis()}",
            sourceApp = event.sourceApp,
            grossValue = earnedAmount,
            distanceKm = distance,
            durationMinutes = minutes,
            status = RideStatus.COMPLETED,
            signalGrade = grade,
            timestamp = System.currentTimeMillis()
        )
        customRideRepo?.recordRide(rideRecord)

        // 2. Grava lançamento de ganho financeiro automático no dia
        val entry = FinancialEntry(
            id = "auto-${System.currentTimeMillis()}",
            type = EntryType.EARNING,
            amount = earnedAmount,
            timestamp = LocalDateTime.now(),
            earningSource = source,
            distanceKm = distance,
            notes = "Automático: ${event.sourceApp} (%.1f km)".format(distance)
        )
        customFinancialRepo?.addEntry(entry)

        // 3. Atualiza a jornada ativa no banco
        customJourneyRepo?.addRideToActiveJourney(
            earnings = earnedAmount,
            km = distance
        )

        // 4. Mensagem de confirmação visível de relance
        _autoReportNotice.value = "✓ Viagem ${event.sourceApp} registrada: + R$ %.2f".format(earnedAmount)

        // Limpa a oferta atual
        _latestOffer.value = null
        _latestEvaluation.value = null
    }

    /**
     * Disparado se a corrida foi cancelada.
     */
    fun onRideCanceled() {
        _activeRideStatus.value = RideEventType.CANCELED
        _latestOffer.value = null
        _latestEvaluation.value = null
        _isOverlayExpanded.value = false
        _autoReportNotice.value = "Viagem cancelada"
    }

    fun setOverlayExpanded(expanded: Boolean) {
        _isOverlayExpanded.value = expanded
    }

    fun toggleOverlayExpanded() {
        _isOverlayExpanded.value = !_isOverlayExpanded.value
    }

    fun updateJourneyEarnings(earnings: Double) {
        _journeyEarnings.value = earnings
    }

    fun clearNotice() {
        _autoReportNotice.value = null
    }
}
