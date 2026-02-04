@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.composable.FilterSubtitle
import com.sharapov.core_ui.theme.icons.Close
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.domain_anime.entity.details.ScoreStats
import com.sharapov.domain_anime.entity.details.StatusStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun StatisticBarsColumn(
    modifier: Modifier = Modifier,
    labelList: List<String>,
    countList: List<Int>,
    fillColor: Color = MaterialTheme.colorScheme.secondary,
    emptyColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val textMeasurer = rememberTextMeasurer()
    val rowHeight = 36.dp
    val countProportions = countProportions(countList)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(labelList.size * rowHeight)
    ) {
        labelList.forEachIndexed { index, label ->
            val lineY = rowHeight.toPx() * index + rowHeight.toPx() / 2f

            drawStatisticBar(
                label = label,
                count = countList[index],
                countProportion = countProportions[index],
                fillColor = fillColor,
                emptyColor = emptyColor,
                textMeasurer = textMeasurer,
                lineY = lineY,
                strokeWidthPx = 8.dp.toPx()
            )
        }
    }
}

@Composable
private fun countProportions(
    countList: List<Int>
): List<Float> {
    val maxCount = countList.maxOfOrNull { it } ?: 0

    return countList.map { count ->
        val target =
            if (maxCount == 0) 0f
            else count.toFloat() / maxCount

        target
    }
}

private fun DrawScope.drawStatisticBar(
    label: String,
    count: Int,
    countProportion: Float,
    fillColor: Color,
    emptyColor: Color,
    textMeasurer: TextMeasurer,
    lineY: Float,
    strokeWidthPx: Float
) {
    val textLayoutResultScore = textMeasurer.measure(
        text = label,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = CustomFonts.Poppins
        )
    )

    val textLayoutResultCount = textMeasurer.measure(
        text = count.toString(),
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = CustomFonts.Poppins
        )
    )

    drawLine(
        color = emptyColor,
        start = Offset(0f, lineY),
        end = Offset(size.width, lineY),
        strokeWidth = strokeWidthPx,
        cap = StrokeCap.Round
    )

    drawLine(
        color = fillColor,
        start = Offset(0f, lineY),
        end = Offset(size.width * countProportion, lineY),
        strokeWidth = strokeWidthPx,
        cap = StrokeCap.Round
    )

    drawText(
        textLayoutResult = textLayoutResultScore,
        topLeft = Offset(
            x = 0f,
            y = lineY - textLayoutResultScore.size.height - 4.dp.toPx()
        ),
        color = fillColor
    )

    drawText(
        textLayoutResult = textLayoutResultCount,
        topLeft = Offset(
            x = size.width - textLayoutResultCount.size.width,
            y = lineY - textLayoutResultScore.size.height - 4.dp.toPx()
        ),
        color = fillColor
    )
}


@Composable
internal fun StatisticBottomSheet(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    statusStats: List<StatusStats>,
    scoreStats: List<ScoreStats>,
    onDismiss: () -> Unit,
    showBottomSheet: Boolean,
    scope: CoroutineScope,
    sheetState: SheetState
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight()
                .windowInsetsPadding(
                    WindowInsets(top = innerPadding.calculateTopPadding())
                ),
            onDismissRequest = {
                onDismiss()
            },
            dragHandle = null,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(horizontal = 8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = CustomIcons.Filled.Close,
                        contentDescription = "Close filters"
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Statistics",
                        fontFamily = CustomFonts.Poppins,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            LazyColumn() {
                item {
                    FilterSubtitle(modifier = Modifier.padding(vertical = 8.dp), text = "Rating")
                    if (scoreStats.isNotEmpty()) {
                        StatisticBarsColumn(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            labelList = scoreStats.map { it.score.toString() },
                            countList = scoreStats.map { it.count }
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            ErrorWithImage(
                                "Бака сервер потерял записи, хех",
                                R.drawable.confused_anime_girl
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    FilterSubtitle(modifier = Modifier.padding(bottom = 8.dp), text = "Lists")
                    if (statusStats.isNotEmpty()) {
                        StatisticBarsColumn(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            labelList = statusStats.map { it.kind.value },
                            countList = statusStats.map { it.count }
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            ErrorWithImage(
                                "Бака сервер потерял записи, хех",
                                R.drawable.confused_anime_girl
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
