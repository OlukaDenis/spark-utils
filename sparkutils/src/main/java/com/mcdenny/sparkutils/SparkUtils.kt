package com.mcdenny.sparkutils

import com.mcdenny.sparkutils.SparkUtils.formatDate
import java.security.SecureRandom
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object SparkUtils {

    private  val DATE_FORMATS = arrayOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSS",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd",
        "yyyy/MM/dd'T'HH:mm:ss.SSSS",
        "yyyy/MM/dd'T'HH:mm:ss.SSSZ",
        "yyyy/MM/dd'T'HH:mm:ssZ",
        "yyyy/MM/dd'T'HH:mm:ss",
        "yyyy/MM/dd HH:mm:ss",
        "yyyy/MM/dd",
        "dd/MM/yyyy",
        "dd-MM-yyyy",
        "dd/MM/yyyy HH:mm:ss",
        "dd-MM-yyyy HH:mm:ss",
        "dd-MM-yyyy HH:mm:ss.SSS",
        "dd-MM-yyyy HH:mm:ss.SSSZ",
        "dd-MM-yyyy HH:mm:ss.SSSXXX",
        "MM/dd/yyyy HH:mm:ss",
        "MM/dd/yyyy",
        "MM-dd-yyyy",
        "MM-dd-yyyy HH:mm:ss",
    )

    @JvmStatic
    @JvmOverloads
    fun Double.formatNumber(currencyCode: String? = "", pattern: String? = "#,###,###"): String {
        val formatter = DecimalFormat(pattern)
        return if (currencyCode.isNullOrEmpty()) formatter.format(this) else "$currencyCode ${
            formatter.format(
                this
            )
        }"
    }

    @JvmStatic
    @JvmOverloads
    fun Double.formatCurrency(symbol: String? = "UGX", fractionDigits: Int = 0): String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = fractionDigits
        format.currency = Currency.getInstance(symbol)

        return format.format(this)
    }

    @JvmStatic
    fun getRandomNumber(): String {
        val random = SecureRandom()
        val randomNumber = random.nextInt(100000000 - 100) + 100
        return randomNumber.toString()
    }

    @JvmStatic
    fun String?.nullOrEmpty(): Boolean {
        return this.isNullOrEmpty()
    }

    @JvmStatic
    fun Long.toKiloBytes(): Int {
        return (this / 1024).toInt()
    }

    @JvmStatic
    fun Long.toMegaBytes(): Int {
        return (this / 1024 / 1024).toInt()
    }

    @JvmStatic
    fun String.maskText(startDigits: Int, endDigits: Int): String {
        val textChars = this.toCharArray()
        val end = this.length - endDigits
        for (i in textChars.indices) {
            if (i in startDigits..<end) {
                textChars[i] = '*'
            }
        }
        return String(textChars)
    }

    @JvmStatic
    @JvmOverloads
    fun todayFormatted(pattern: String = "yyyy-MM-dd HH:mm:ss a"): String {
        val dtf: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val today = Date()
        return dtf.format(today)
    }


    @JvmStatic
    @JvmOverloads
    fun Long.longDateToString(pattern: String = "yyyy-MM-dd HH:mm:ss a"): String {
        return try {
            // Create a Date object from the milliseconds
            val date = Date(this)

            // Define the desired date format
            val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())

            // Format the date to the specified format
            dateFormat.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    @JvmStatic
    @JvmOverloads
    fun String?.formatDate(pattern: String = "yyyy-MM-dd HH:mm:ss a"): String {
        if (this.isNullOrEmpty()) return "";
        return try {
            val date = DATE_FORMATS.asSequence()
                .mapNotNull { format ->
                    runCatching {
                        SimpleDateFormat(format, Locale.getDefault()).parse(this).also {
                            println("Trying format: $format, Result: $it")
                        }
                    }.getOrNull()
                }
                .firstOrNull() ?: throw IllegalArgumentException("Unparseable date: $this")

            val outputTime = SimpleDateFormat(pattern, Locale.getDefault())
            outputTime.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            this.toString()
        }
    }

    @JvmStatic
    fun Any.objectToString(): String {
        if (this is Int) {
            return this.toString()
        }
        return if (this is Double) {
            this.toString()
        } else if (this is Boolean) {
            this.toString()
        } else {
            this.toString()
        }
    }

    @JvmStatic
    fun String.capitalize(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }

    @JvmStatic
    fun String.capitalizeWords(): String {
        return this.split(" ").joinToString(" ") { word ->
            //convert each word to small case inside the lambda
            val smallCaseWord = word.lowercase()
            //finish off by capitalizing to title case
            smallCaseWord.replaceFirstChar(Char::titlecaseChar)
        }
    }

    @JvmStatic
    fun String.isOnlyAlphabets(): Boolean = this.matches("^[\\p{L} ,.\'-]*$".toRegex())

    @JvmStatic
    fun String.toMomentAgo(): String? {
        var convTime: String? = null
        try {
//            val dateFormat = SimpleDateFormat(datePattern, Locale.UK)
//            val pasTime = dateFormat.parse(this)

            val pasTime = DATE_FORMATS.asSequence()
                .mapNotNull { format ->
                    runCatching {
                        SimpleDateFormat(format, Locale.getDefault()).parse(this).also {
                            println("Trying format: $format, Result: $it")
                        }
                    }.getOrNull()
                }
                .firstOrNull() ?: throw IllegalArgumentException("Unparseable date: $this")


            val nowTime = System.currentTimeMillis()
            val dateDiff = nowTime - pasTime.time
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

            when {
                second < 60 -> {
                    convTime = "${second}s"
                }
                minute < 60 -> {
                    convTime = "${minute}min"
                }
                hour < 24 -> {
                    convTime = "${hour}h"
                }
                day >= 7 -> {
                    convTime = when {
                        day > 360 -> {
                            (day / 360).toString() + "y"
                        }
                        day > 30 -> {
                            (day / 30).toString() + "m"
                        }
                        else -> {
                            (day / 7).toString() + "w"
                        }
                    }
                }
                day < 7 -> {
                    convTime = "${day}d"
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convTime
    }

    @JvmStatic
    fun String.stringToList(delimiters: String = " ") = this.trim().splitToSequence(delimiters)
        .filter { this.isNotEmpty() }
        .toList()
}