package cat.naval.atalaya.base.signal

abstract class SignalMeasure<T>(
    val name: String,
    val extractor: (T) -> Number?,
    val unit: String?,
    val iconDrawable: Int = 1,
    val minValue: Float,
    val maxValue: Float
) {
    abstract fun evaluate(value: Float?): SignalQuality
}