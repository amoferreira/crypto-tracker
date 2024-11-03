package com.amoferreira.cryptotracker.crypto.presentation.models

import java.text.NumberFormat

data class DisplayableNumber(
    val value: Double,
    val formattedNumber: String,
)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formattedNumber = formatter.format(this),
    )
}

