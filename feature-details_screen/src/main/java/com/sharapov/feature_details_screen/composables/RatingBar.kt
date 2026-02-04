package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Star


@Composable
internal fun StarsRating(
    modifier: Modifier = Modifier,
    mean: Double
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "Rating: $mean",
            fontFamily = CustomFonts.Poppins,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            textAlign = TextAlign.End,
            fontStyle = FontStyle.Italic
        )
        RatingBar(
            rating = mean.toFloat(),
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            foregroundColor = MaterialTheme.colorScheme.secondary
        )
    }

}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    spaceBetween: Dp = 0.dp,
    backgroundColor: Color,
    foregroundColor: Color
) {
    val normalizedRating = rating / 2
    val background = rememberVectorPainter(CustomIcons.Filled.Star)
    val foreground = rememberVectorPainter(CustomIcons.Filled.Star)

    val density = LocalDensity.current
    val spacePx = with(density) { spaceBetween.toPx() }

    val starWidth = background.intrinsicSize.width
    val starHeight = background.intrinsicSize.height

    val totalCount = 5
    val totalWidth =
        starWidth * totalCount + spacePx * (totalCount - 1)

    Box(
        modifier = modifier
            .width(with(density) { totalWidth.toDp() })
            .height(with(density) { starHeight.toDp() })
            .drawBehind {
                drawRating(
                    rating = normalizedRating,
                    background = background,
                    foreground = foreground,
                    space = spacePx,
                    starWidth = starWidth,
                    starHeight = starHeight,
                    backgroundColor = backgroundColor,
                    foregroundColor = foregroundColor,
                )
            }
    )
}


private fun DrawScope.drawRating(
    rating: Float,
    background: VectorPainter,
    backgroundColor: Color,
    foreground: VectorPainter,
    foregroundColor: Color,
    space: Float,
    starWidth: Float,
    starHeight: Float
) {
    val totalCount = 5
    val clampedRating = rating.coerceIn(0f, totalCount.toFloat())

    val fullStars = clampedRating.toInt()
    val remainder = clampedRating - fullStars

    for (i in 0 until totalCount) {
        val startX = i * (starWidth + space)

        translate(left = startX, top = 0f) {
            with(background) {
                draw(
                    size = Size(starWidth, starHeight),
                    colorFilter = ColorFilter.tint(
                        backgroundColor
                    )
                )
            }
        }
    }

    drawWithLayer {
        for (i in 0 until totalCount) {
            val startX = i * (starWidth + space)

            translate(left = startX, top = 0f) {
                with(foreground) {
                    draw(
                        size = Size(starWidth, starHeight),
                        colorFilter = ColorFilter.tint(
                            foregroundColor
                        )

                    )
                }
            }
        }

        val maskStart =
            fullStars * (starWidth + space) +
                    remainder * starWidth

        val maskWidth =
            size.width - maskStart

        drawRect(
            color = Color.Transparent,
            topLeft = Offset(maskStart, 0f),
            size = Size(maskWidth, starHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(
    block: DrawScope.() -> Unit
) {
    with(drawContext.canvas.nativeCanvas) {
        val checkpoint = saveLayer(null, null)
        block()
        restoreToCount(checkpoint)
    }
}
