package com.amoferreira.cryptotracker.crypto.presentation.coinlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amoferreira.cryptotracker.crypto.presentation.models.DisplayableNumber
import com.amoferreira.cryptotracker.ui.theme.CryptoTrackerTheme
import com.amoferreira.cryptotracker.ui.theme.greenBackground

@Composable
fun PriceChangeItem(
    change: DisplayableNumber,
    modifier: Modifier = Modifier,
) {
    val isChangeNegative = change.value < 0.0
    val contentColor = if (isChangeNegative) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        Color.Green
    }
    val backgroundColor = if (isChangeNegative) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        greenBackground
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(backgroundColor)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isChangeNegative) {
                Icons.Default.KeyboardArrowDown
            } else {
                Icons.Default.KeyboardArrowUp
            },
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor,
        )
        Text(
            text = "${change.formattedNumber} %",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeItemPreviewPositive() {
    CryptoTrackerTheme {
        PriceChangeItem(
            change = DisplayableNumber(
                value = 2.43,
                formattedNumber = "2.43"
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeItemPreviewNegative() {
    CryptoTrackerTheme {
        PriceChangeItem(
            change = DisplayableNumber(
                value = -1.78,
                formattedNumber = "-1.78"
            )
        )
    }
}