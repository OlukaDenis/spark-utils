package com.mcdenny.sparkutils.models

/**
 * Represents the item for a receipt.
 *
 * This class defines the structure of a receipt item
 *
 * @property alignment The alignment of the header content. Accepted values are:
 * - "LEFT": Aligns the content to the left.
 * - "CENTER": Aligns the content to the center.
 * - "RIGHT": Aligns the content to the right.
 * - "BETWEEN": Aligns the content to the right and left.
 */
data class ReceiptItemModel(
    var key: String = "",
    var position: Int = 0,
    var value: String = "",
    var isBold: Boolean = false,
    var alignment: String = "BETWEEN"
)
