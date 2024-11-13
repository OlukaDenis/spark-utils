package com.mcdenny.sparkutils

import java.security.SecureRandom
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

object SparkUtils {
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

    @JvmStatic
    fun formatNumber(number: Double, currencyCode: String, pattern: String): String {
        val formatter = DecimalFormat(pattern)
        return  "$currencyCode ${formatter.format(number)}"
    }

    @JvmStatic
    @JvmOverloads
    fun formatCurrency(value: Double, symbol: String? = "UGX", fractionDigits: Int = 0): String{
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = fractionDigits
        format.currency = Currency.getInstance(symbol)

        return format.format(value)
    }

    @JvmStatic
    fun getRandomNumber(): String {
        val random = SecureRandom()
        val randomNumber = random.nextInt(100000000 - 100) + 100
        return randomNumber.toString()
    }

    @JvmStatic
    fun isNullOrEmpty(value: String?): Boolean {
        return value.isNullOrEmpty()
    }

    @JvmStatic
    fun toKiloBytes(value: Long): Int {
        return (value / 1024).toInt()
    }

    @JvmStatic
    fun toMegaBytes(value: Long): Int {
        return (value / 1024 / 1024).toInt()
    }

    @JvmStatic
    fun maskText(text: String, startDigits: Int, endDigits: Int): String {
        val textChars = text.toCharArray()
        val end = text.length - endDigits
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
    fun longDateToString(time: Long, pattern: String = "yyyy-MM-dd HH:mm:ss a"): String {
        return try {
            // Create a Date object from the milliseconds
            val date = Date(time)

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
    fun formatDate(inputDate: String, pattern: String = "yyyy-MM-dd HH:mm:ss a"): String {
        val formats = arrayOf(
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
        
        return try {
            val date = formats.asSequence()
                .mapNotNull { format -> 
                    runCatching { 
                        SimpleDateFormat(format, Locale.getDefault()).parse(inputDate).also {
                            println("Trying format: $format, Result: $it")
                        } 
                    }.getOrNull() 
                }
                .firstOrNull() ?: throw IllegalArgumentException("Unparseable date: $inputDate")

            val outputTime = SimpleDateFormat(pattern, Locale.getDefault())
            outputTime.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            inputDate
        }
    }

    @JvmStatic
    fun objectToString(`object`: Any): String {
        if (`object` is Int) {
            return `object`.toString()
        }
        return if (`object` is Double) {
            `object`.toString()
        } else if (`object` is Boolean) {
            `object`.toString()
        } else {
            `object`.toString()
        }
    }

    @JvmStatic
    fun capitalize(value: String): String {
        return value.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}