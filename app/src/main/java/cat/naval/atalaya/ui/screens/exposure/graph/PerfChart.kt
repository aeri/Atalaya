package cat.naval.atalaya.ui.screens.exposure.graph

import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath

@Composable
fun PerformanceChart(
    modifier: Modifier = Modifier,
    list: List<Float> = listOf(10f, 20f, 3f, 1f),
    lineColor: Color = Color.Green,
    gradientColor: Color = Color(0x8019C37D)
) {
    if (list.size < 2) return

    val max = list.max()
    val min = list.min()

    Canvas(modifier = modifier) {
        val chartWidth = size.width
        val chartHeight = size.height


        val visiblePoints = list.takeLast(10)

        val step = chartWidth / (visiblePoints.size - 1)

        val points = visiblePoints.mapIndexed { index, value ->
            val percentage = getValuePercentageForRange(value, max, min)
            Offset(
                x = step * index,
                y = chartHeight * (1 - percentage)
            )
        }

        val areaPath = Path().apply {
            moveTo(points.first().x, chartHeight)
            points.forEach { point ->
                lineTo(point.x, point.y)
            }
            lineTo(points.last().x, chartHeight)
            close()
        }

        drawPath(
            path = areaPath.asComposePath(),
            brush = Brush.verticalGradient(
                colors = listOf(gradientColor, Color.Transparent),
                startY = 0f,
                endY = chartHeight
            )
        )

        for (i in 0 until points.size - 1) {
            drawLine(
                color = lineColor,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 3f
            )
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    return if (max == min) 0.5f else (value - min) / (max - min)
}
