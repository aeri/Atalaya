package cat.naval.atalaya.base.signal

import cat.naval.atalaya.base.signal.SignalQuality

data class SignalInfo(
    val iconDrawable: Int,
    val name: String,
    val values: List<Float>,
    val currentValue: Float,
    val unit: String?,
    val quality: SignalQuality,
    val maxValue: Float,
    val minValue: Float,
)