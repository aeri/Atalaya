package cat.naval.atalaya.ui.screens.exposure.graph

import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import cat.naval.atalaya.base.signal.SignalQuality

@Composable
fun PerformanceChart(
    modifier: Modifier = Modifier,
    list: List<Float> = listOf(10f, 20f, 3f, 1f),
    quality: SignalQuality,
    defaultMinY: Float = list.minOrNull() ?: 0f,
    defaultMaxY: Float = list.maxOrNull() ?: 0f

) {
    if (list.size < 2) return


    val minY = defaultMinY
    val maxY = defaultMaxY

    val lineColor = when (quality) {
        SignalQuality.NONE -> Color(0x80ff3d00)
        SignalQuality.POOR -> Color(0x80ff6d00)
        SignalQuality.MODERATE -> Color(0x80d9bb40)
        SignalQuality.GOOD -> Color(0x80b6e94f)
        SignalQuality.GREAT -> Color(0x8019C37D)
    }

    val gradientColor = lineColor.copy(alpha = 0.5f)


    Canvas(modifier = modifier) {
        val chartWidth = size.width
        val chartHeight = size.height

        val visiblePoints = list.takeLast(10)
        val step = chartWidth / (visiblePoints.size - 1)

        val points = visiblePoints.mapIndexed { index, value ->
            val percentage = (value - minY) / (maxY - minY)
            Offset(
                x = step * index,
                y = chartHeight * (1 - percentage.coerceIn(0f, 1f))
            )
        }

        val areaPath = Path().apply {
            moveTo(points.first().x, chartHeight)
            points.forEach { point -> lineTo(point.x, point.y) }
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
