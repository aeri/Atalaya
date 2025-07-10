package cat.naval.atalaya.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun PerformanceChart(modifier: Modifier = Modifier, list: List<Float> = listOf(10f, 20f, 3f, 1f)) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()

    if (list.isNotEmpty()) {
        Row(modifier = modifier) {
            val max = list.max()
            val min = list.min()

            val lineColor =
                if (list.last() > list.first()) Color.Green else Color.Red

            for (pair in zipList) {

                val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
                val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onDraw = {
                        val fromPoint = Offset(
                            x = 0f,
                            y = size.height.times(1 - fromValuePercentage)
                        ) // <-- Use times so it works for any available space
                        val toPoint =
                            Offset(
                                x = size.width,
                                y = size.height.times(1 - toValuePercentage)
                            ) // <-- Also here!

                        drawLine(
                            color = lineColor,
                            start = fromPoint,
                            end = toPoint,
                            strokeWidth = 3f
                        )
                    })
            }
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)