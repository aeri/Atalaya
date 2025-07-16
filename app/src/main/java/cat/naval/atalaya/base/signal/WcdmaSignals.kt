package cat.naval.atalaya.base.signal

import cat.naval.atalaya.base.signal.SignalMeasure
import cat.naval.atalaya.base.signal.SignalQuality


class WcdmaRssiSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RSSI",
    extractor = extractor,
    unit = "dBm",
    minValue = -120f,
    maxValue = -50f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -110 -> SignalQuality.NONE
            value > -110 && value < -100 -> SignalQuality.POOR
            value > -100 && value <= -86 -> SignalQuality.MODERATE
            value >= -85 && value < -70 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}


class WcdmaRscpSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RSCP",
    extractor = extractor,
    unit = "dBm",
    minValue = -120f,
    maxValue = -20f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -95 -> SignalQuality.NONE
            value > -95 && value <= -85 -> SignalQuality.POOR
            value > -85 && value <= -75 -> SignalQuality.MODERATE
            value > -75 && value < -60 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}

class WcdmaEcnoSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "Ec/No",
    extractor = extractor,
    unit = "dB",
    minValue = -20f,
    maxValue = 0f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -9 -> SignalQuality.NONE
            value > -9 && value <= -6 -> SignalQuality.POOR
            value > -6 && value <= -1.5 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}