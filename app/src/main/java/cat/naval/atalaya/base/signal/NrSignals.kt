package cat.naval.atalaya.base.signal


class NrRsrpSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "SS RSRP",
    extractor = extractor,
    unit = "dBm",
    minValue = -130f,
    maxValue = -40f
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


class NrRsrqSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "SS RSRQ",
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

class NrSnrSignal<T>(extractor: (T) -> Number?) : SignalMeasure<T>(
    name = "SS SNR",
    extractor = extractor,
    unit = "dB",
    minValue = 0f,
    maxValue = 30f
) {
    override fun evaluate(value: Float?): SignalQuality {
        if (value == null) return SignalQuality.NONE
        return when {
            value <= 0 -> SignalQuality.NONE
            value > 0 && value <= 13 -> SignalQuality.POOR
            value > 13 && value < 20 -> SignalQuality.GOOD
            else -> SignalQuality.GREAT
        }
    }
}