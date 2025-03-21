package com.mcdenny.sparkutils.models

/**
 * Represents the copy for a receipt.
 *
 * This class defines the structure of a receipt item
 *
 * @property alignment The alignment of the header content. Accepted values are:
 * - "LEFT": Aligns the content to the left.
 * - "CENTER": Aligns the content to the center.
 * - "RIGHT": Aligns the content to the right.
 */
data class ReceiptCopyModel(
  var name: String = "",
  var isBold: Boolean = false,
  var alignment: String = ""
)
