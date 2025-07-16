package cat.naval.atalaya.base.signal

class GsmRxlSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "RXL",
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

class GsmTaSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "TA",
    extractor = extractor,
    unit = null,
    minValue = 0f,
    maxValue = 60f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value >= 50 -> SignalQuality.POOR
            value < 50 && value >= 30 -> SignalQuality.MODERATE
            value < 30 && value >= 20 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}