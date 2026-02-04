package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts

@Composable
internal fun InfoRow(
    modifier: Modifier = Modifier,
    labelList: List<String>,
    contentList: List<String>
) {
    require(labelList.size == contentList.size) { "Lists must have same size" }
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        contentList.forEachIndexed { index, content ->
            InfoColumn(
                modifier = Modifier.weight(1f),
                label = labelList[index],
                content = content
            )
        }
    }
}

@Composable
private fun InfoColumn(
    modifier: Modifier = Modifier,
    label: String,
    content: String
) {
    Column(
        modifier = modifier
            .heightIn(min = 56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontFamily = CustomFonts.Poppins,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = content,
                textAlign = TextAlign.Center,
                fontFamily = CustomFonts.Poppins,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )
        }
    }
}
