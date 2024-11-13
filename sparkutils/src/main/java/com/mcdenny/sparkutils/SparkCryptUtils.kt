package com.mcdenny.sparkutils

import java.util.Locale
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object SparkCryptUtils {

    /**
     * Convert hex string to bytes
     */
    @JvmStatic
    fun hexToBytes(hexStr: String): ByteArray? {
        if (hexStr.isEmpty()) {
            return null
        }
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(
                16
            )
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }

    /**
     * Convert bytes to hex string
     */
    @JvmStatic
    fun bytesToHex(buf: ByteArray): String {
        val sb = StringBuffer()
        for (i in buf.indices) {
            var hex = Integer.toHexString(buf[i].toInt() and 0xFF)
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }

    /**
     * Convert hex value to ascii code
     **/
    @JvmStatic
    fun hexToAsciiString(hex: String): String {
        val sb = StringBuilder()
        val temp = StringBuilder()

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        var i = 0
        while (i < hex.length - 1) {
            //grab the hex in pairs
            val output = hex.substring(i, i + 2)
            //convert hex to decimal
            val decimal = output.toInt(16)
            //convert the decimal to character
            sb.append(decimal.toChar())
            temp.append(decimal)
            i += 2
        }
        return sb.toString()
    }

    /**
     * convert int to byte[]
     * @param i integer to be converted to byte array
     * @return byte array
     */
    @JvmStatic
    fun intToByteArray(i: Int): ByteArray {
        val result = ByteArray(2)
        result[0] = (i shr 8 and 0xFF).toByte()
        result[1] = (i and 0xFF).toByte()
        return result
    }


    @JvmStatic
    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }

    @JvmStatic
    fun intToHexString(i: Int): String {
        var string: String? = null
        string = if (i in 0..9) {
            "0$i"
        } else {
            Integer.toHexString(i)
        }
        if (string!!.length == 2) {
            string = "00$string"
        } else if (string.length == 1) {
            string = "000$string"
        } else if (string.length == 3) {
            string = "0$string"
        }
        return string
    }


    /**
     * convert hexadecimal byte to int
     *
     * @param b
     * @return
     */
    @JvmStatic
    fun byteArrayToInt(b: ByteArray): Int {
        var result = 0
        for (i in b.indices) {
            result = result shl 8
            result = result or (b[i].toInt() and 0xff) //
        }
        return result
    }

    fun extractKCVFromKey(keyHex: String): String {
        return try {
            // Convert the key to a byte array
            val keyByes = hexToBytes(keyHex) ?: return ""

            // Define a block of 8 zero bytes
            val zeroBlock = ByteArray(16)

            // Encrypt the zeroBlock using the extended Key
            val encryptedData: ByteArray =  encrypt3DES(keyByes, zeroBlock)

            // Take the first 3 bytes (6 hexadecimal digits) as the KCV
            val result: String = bytesToHex(encryptedData)
            val res = result.trim { it <= ' ' }.substring(0, 16)
            res.trim { it <= ' ' }
        } catch (e: Exception) {
            ""
        }
    }

    @JvmStatic
    @Throws(java.lang.Exception::class)
    fun encrypt3DES(value: ByteArray, data: ByteArray?): ByteArray {
        val cipher: Cipher
        if (value.size == 8) { // Single-length (64-bit) TMK
            cipher = Cipher.getInstance("DES/ECB/NoPadding")
            val desKey = SecretKeySpec(value, "DES")
            cipher.init(Cipher.ENCRYPT_MODE, desKey)
        } else if (value.size == 16) { // Double-length (128-bit) TMK
            cipher = Cipher.getInstance("DESede/ECB/NoPadding") // 3DES cipher
            val desKey = SecretKeySpec(value, "DESede")
            cipher.init(Cipher.ENCRYPT_MODE, desKey)
        } else {
            throw IllegalArgumentException("value must be either single (16 hex) or double-length (32 hex)")
        }
        return cipher.doFinal(data)
    }
}