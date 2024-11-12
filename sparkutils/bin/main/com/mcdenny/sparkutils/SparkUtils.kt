package com.mcdenny.sparkutils

import java.text.DecimalFormat

public class SparkUtils {
    companion object {
        @JvmStatic
        fun formatNumber(number: Double): String {
            val formatter = DecimalFormat("#,###,###")
            return formatter.format(number)
        }

        @JvmStatic
        fun formatNumber(number: Double, currencyCode: String): String {
            val formatter = DecimalFormat("#,###,###")
            return  "$currencyCode ${formatter.format(number)}"
        }

        public fun regularFunction(): String {
            return "Hello from SparkUtils!"
        }

        @JvmStatic
        fun formatNumber(number: Double, currencyCode: String, pattern: String): String {
            val formatter = DecimalFormat(pattern)
            return  "$currencyCode ${formatter.format(number)}"
        }

    }
}