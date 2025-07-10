package cat.naval.atalaya.ui.screens

data class SignalInfo(
    val iconDrawable: Int,
    val name: String,
    val tickerName: String,
    val values: List<Float>,
    val currentValue: Float,
    val unit: String?,
)