package com.mcdenny.sparkutils

import java.text.DecimalFormat

class SparkUtils {
    companion object {
        fun formatAmount(amount: Double): String {
            val formatter = DecimalFormat("#,###,###")
            return formatter.format(amount)
        }

        fun formatAmount(amount: Double, currencyCode: String): String {
            val formatter = DecimalFormat("#,###,###")
            return  "$currencyCode ${formatter.format(amount)}"
        }

    }
}