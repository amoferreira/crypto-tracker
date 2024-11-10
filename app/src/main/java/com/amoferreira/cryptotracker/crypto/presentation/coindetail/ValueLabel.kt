package com.amoferreira.cryptotracker.crypto.presentation.coindetail

import java.text.NumberFormat
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unit: String,
) {
    fun format(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
                value > 1000 -> 0
                value in 2f .. 999f -> 2
                else -> 3
            }
            maximumFractionDigits = fractionDigits
            minimumFractionDigits = 0
        }
        val formattedValue = formatter.format(value)
        return "$formattedValue$unit"
    }
}
