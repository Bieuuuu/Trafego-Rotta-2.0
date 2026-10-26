package com.traffigorotta.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.traffigorotta.model.RideOffer

/**
 * Serviço de Acessibilidade Rotta (Fase 2)
 *
 * Limites Inegociáveis (AGENTS.md):
 * 1. O app NÃO aceita, recusa nem toca em nada nos apps de terceiros.
 *    NUNCA implementa dispatchGesture nem performAction(ACTION_CLICK).
 * 2. Acessibilidade ESTRITAMENTE para leitura de dados numéricos da oferta (R$, km, min) e estado da corrida.
 * 3. Minimização estrita: não processa nem salva dados do passageiro.
 * 4. Processamento 100% no aparelho (LGPD).
 */
class RottaAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            packageNames = arrayOf("com.ubercab.driver", "com.taxis99.driver", "com.taxis99")
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
            flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            // NUNCA setar canPerformGestures = true
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val pkgName = event.packageName?.toString() ?: return
        if (!pkgName.contains("ubercab") && !pkgName.contains("taxis99")) return

        val rootNode = rootInActiveWindow ?: return

        try {
            val texts = mutableListOf<String>()
            extractNodeTexts(rootNode, texts)

            if (texts.isNotEmpty()) {
                val rideEvent = OfferTextParser.parseEvent(texts, pkgName)
                if (rideEvent != null) {
                    when (rideEvent.type) {
                        RideEventType.OFFER -> {
                            val offer = RideOffer(
                                id = "offer-${rideEvent.timestamp}",
                                sourceApp = rideEvent.sourceApp,
                                grossValue = rideEvent.grossValue,
                                distanceKm = rideEvent.distanceKm,
                                durationMinutes = rideEvent.durationMinutes,
                                timestamp = rideEvent.timestamp
                            )
                            OfferCoordinator.onNewOfferReceived(offer)
                            OfferCoordinator.latestEvaluation.value?.let { eval ->
                                RottaFeedbackNotifier.notifyOfferEvaluation(
                                    applicationContext,
                                    eval,
                                    OfferCoordinator.config.value
                                )
                            }
                        }
                        RideEventType.ACCEPTED -> {
                            OfferCoordinator.onRideAccepted(rideEvent)
                        }
                        RideEventType.COMPLETED -> {
                            OfferCoordinator.onRideCompleted(rideEvent)
                        }
                        RideEventType.CANCELED -> {
                            OfferCoordinator.onRideCanceled()
                        }
                    }
                }
            }
        } finally {
            rootNode.recycle()
        }
    }

    private fun extractNodeTexts(node: AccessibilityNodeInfo?, output: MutableList<String>) {
        if (node == null) return

        node.text?.toString()?.takeIf { it.isNotBlank() }?.let { output.add(it) }
        node.contentDescription?.toString()?.takeIf { it.isNotBlank() }?.let { output.add(it) }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                extractNodeTexts(child, output)
                child.recycle()
            }
        }
    }

    override fun onInterrupt() {
        // Sem recursos bloqueantes para liberar
    }
}
