package com.traffigorotta.service

import com.traffigorotta.data.FinancialRepository
import com.traffigorotta.data.JourneyRepository
import com.traffigorotta.data.RideRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class OfferTextParserTest {

    @Test
    fun parseUberOffer_extractsNumericalFieldsCorrectly() {
        val texts = listOf(
            "UberX",
            "R$ 38,50",
            "12,0 km",
            "24 min de viagem",
            "Ponto de encontro a 2 km"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.ubercab.driver")

        assertNotNull(offer)
        assertEquals("Uber", offer?.sourceApp)
        assertEquals(38.50, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(12.0, offer?.distanceKm ?: 0.0, 0.01)
        assertEquals(24, offer?.durationMinutes)
    }

    @Test
    fun parse99Offer_extractsNumericalFieldsCorrectly() {
        val texts = listOf(
            "Corrida 99Pop",
            "R$ 19,00",
            "7,5 km",
            "28 mins"
        )

        val offer = OfferTextParser.parseTexts(texts, "com.taxis99.driver")

        assertNotNull(offer)
        assertEquals("99", offer?.sourceApp)
        assertEquals(19.00, offer?.grossValue ?: 0.0, 0.01)
        assertEquals(7.5, offer?.distanceKm ?: 0.0, 0.01)
        assertEquals(28, offer?.durationMinutes)
    }

    @Test
    fun parseEvent_uberOffer_identifiesOfferType() {
        val texts = listOf(
            "Uber Black",
            "R$ 74,20",
            "22,4 km",
            "38 min"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.OFFER, event?.type)
        assertEquals(74.20, event?.grossValue ?: 0.0, 0.01)
        assertEquals(22.4, event?.distanceKm ?: 0.0, 0.01)
        assertEquals(38, event?.durationMinutes)
    }

    @Test
    fun parseEvent_acceptedRide_identifiesAcceptedType() {
        val texts = listOf(
            "A caminho do passageiro",
            "Dirija até o ponto de embarque na Av. Paulista",
            "Chegada em 4 min"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.ACCEPTED, event?.type)
    }

    @Test
    fun parseEvent_completedRide_identifiesCompletedTypeAndExtractsFare() {
        val texts = listOf(
            "Viagem Concluída",
            "Você ganhou R$ 48,90 nesta viagem",
            "Distância: 15,2 km • 32 min"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.COMPLETED, event?.type)
        assertEquals(48.90, event?.grossValue ?: 0.0, 0.01)
        assertEquals(15.2, event?.distanceKm ?: 0.0, 0.01)
        assertEquals(32, event?.durationMinutes)
    }

    @Test
    fun parseEvent_99CompletedRide_identifiesCompletedType() {
        val texts = listOf(
            "Corrida finalizada",
            "Valor final da viagem: R$ 26,40",
            "8,1 km percorridos"
        )

        val event = OfferTextParser.parseEvent(texts, "com.taxis99.driver")
        assertNotNull(event)
        assertEquals(RideEventType.COMPLETED, event?.type)
        assertEquals(26.40, event?.grossValue ?: 0.0, 0.01)
        assertEquals(8.1, event?.distanceKm ?: 0.0, 0.01)
    }

    @Test
    fun parseEvent_canceledRide_identifiesCanceledType() {
        val texts = listOf(
            "O passageiro cancelou a viagem",
            "Você está disponível para novas viagens"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.CANCELED, event?.type)
    }

    @Test
    fun parseEvent_shortTripInMeters_parsesCorrectly() {
        val texts = listOf(
            "UberX",
            "R$ 8,00",
            "800m",
            "3 min"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.OFFER, event?.type)
        assertEquals(8.00, event?.grossValue ?: 0.0, 0.01)
        assertEquals(0.8, event?.distanceKm ?: 0.0, 0.01)
    }

    @Test
    fun parseEvent_longRegionalTripWithHours_parsesCorrectly() {
        val texts = listOf(
            "UberX VIP",
            "R$ 215,80",
            "92,5 km",
            "1 h 35 min de viagem"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNotNull(event)
        assertEquals(RideEventType.OFFER, event?.type)
        assertEquals(215.80, event?.grossValue ?: 0.0, 0.01)
        assertEquals(92.5, event?.distanceKm ?: 0.0, 0.01)
        assertEquals(95, event?.durationMinutes) // 1h (60) + 35m = 95m
    }

    @Test
    fun parseEmptyOrIrrelevantScreen_returnsNullSafely() {
        val texts = listOf(
            "Você está online",
            "Procurando viagens",
            "Toque para ver mapa"
        )

        val event = OfferTextParser.parseEvent(texts, "com.ubercab.driver")
        assertNull(event)
    }

    @Test
    fun offerCoordinator_onRideCompleted_automaticallyUpdatesJourneyAndCreatesFinancialEntry() {
        val journeyRepo = JourneyRepository()
        val financialRepo = FinancialRepository()
        val rideRepo = RideRepository()

        // Inicia jornada limpa
        journeyRepo.startJourney()
        val initialEarnings = journeyRepo.activeJourney.value?.grossEarnings ?: 0.0

        val completedEvent = ParsedRideEvent(
            type = RideEventType.COMPLETED,
            sourceApp = "Uber",
            grossValue = 50.00,
            distanceKm = 15.0,
            durationMinutes = 30,
            rawSummary = "Viagem concluída R$ 50,00"
        )

        OfferCoordinator.onRideCompleted(
            event = completedEvent,
            customJourneyRepo = journeyRepo,
            customFinancialRepo = financialRepo,
            customRideRepo = rideRepo
        )

        // Verifica se os ganhos e quilômetros foram acrescidos automaticamente
        val updatedJourney = journeyRepo.activeJourney.value
        assertNotNull(updatedJourney)
        assertEquals(initialEarnings + 50.00, updatedJourney?.grossEarnings ?: 0.0, 0.01)
        assertEquals(15.0, updatedJourney?.distanceKm ?: 0.0, 0.01)
        assertEquals(1, updatedJourney?.completedRidesCount)

        // Verifica se o lançamento financeiro automático foi criado
        val latestEntry = financialRepo.entries.value.firstOrNull { it.notes.contains("Automático: Uber") }
        assertNotNull(latestEntry)
        assertEquals(50.00, latestEntry?.amount ?: 0.0, 0.01)
    }
}
