package cat.naval.atalaya.base.signal

import cat.naval.atalaya.base.signal.SignalMeasure
import cat.naval.atalaya.base.signal.SignalQuality

class LteRssiSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RSSI",
    extractor = extractor,
    unit = "dBm",
    minValue = -120f,
    maxValue = -40f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -95 -> SignalQuality.NONE
            value > -95 && value <= -85 -> SignalQuality.POOR
            value > -85 && value <= -75 -> SignalQuality.MODERATE
            value > -75 && value < -65 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}

class LteRsrpSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RSRP",
    extractor = extractor,
    unit = "dBm",
    minValue = -120f,
    maxValue = -20f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -100 -> SignalQuality.NONE
            value > -100 && value <= -90 -> SignalQuality.POOR
            value > -90 && value <= -80 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}

class LteRsrqSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RSRQ",
    extractor = extractor,
    unit = "dB",
    minValue = -20f,
    maxValue = 0f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= -20 -> SignalQuality.NONE
            value > -20 && value <= -15 -> SignalQuality.POOR
            value > -15 && value <= -10 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}

class LteSnrSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "SNR",
    extractor = extractor,
    unit = "dB",
    minValue = 0f,
    maxValue = 30f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= 0 -> SignalQuality.POOR
            value > 0 && value <= 13 -> SignalQuality.MODERATE
            value > 13 && value < 20 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}