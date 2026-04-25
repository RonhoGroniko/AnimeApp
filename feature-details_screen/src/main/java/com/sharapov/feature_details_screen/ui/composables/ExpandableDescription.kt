package com.sharapov.feature_details_screen.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.KeyboardArrowDown

@Composable
internal fun ExpandableDescription(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 4
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var hasOverflow by remember { mutableStateOf(false) }

    val fadeBg = MaterialTheme.colorScheme.surfaceVariant
    val fadeHeight = 48.dp
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "arrow_rotation")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .animateContentSize()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        drawContent()
                        if (!expanded && hasOverflow) {
                            val height = fadeHeight.toPx()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, fadeBg),
                                    startY = size.height - height,
                                    endY = size.height
                                )
                            )
                        }
                    },
                maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { hasOverflow = it.hasVisualOverflow },
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontFamily = CustomFonts.Poppins
            )

            if (hasOverflow || expanded) {
                val interaction = remember { MutableInteractionSource() }
                Icon(
                    imageVector = CustomIcons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .graphicsLayer { rotationZ = rotation }
                        .size(24.dp)
                        .clickable(
                            indication = null,
                            interactionSource = interaction,
                        ) { expanded = !expanded }
                        .padding(bottom = 4.dp)
                )
            }
        }
    }
}