package com.mcdenny.sparkutils.models

/**
 * Represents the header or footer model for a receipt.
 *
 * This class defines the structure of a receipt header, including its type, text, image, formatting options, and alignment.
 *
 * @property type The type of the header. Accepted values are:
 * - "ALL": Represents a header that includes both text and an image.
 * - "TEXT": Represents a header that only includes text.
 * - "IMAGE": Represents a header that only includes an image.
 * - The default type is TEXT
 * @property text The text content of the header. This is used when [type] is "TEXT" or "ALL".
 * @property image The image URL or path for the header. This is used when [type] is "IMAGE" or "ALL".
 * @property isBold Indicates whether the header text should be displayed in bold. Accepts "true" or "false" as values.
 * @property divider A visual divider to separate the header from other sections. Can be a string like a line or a custom character.
 * @property alignment The alignment of the header content. Accepted values are:
 * - "LEFT": Aligns the header content to the left.
 * - "CENTER": Aligns the header content to the center.
 * - "RIGHT": Aligns the header content to the right.
 * - The default alignment is center
 */
data class ReceiptHeaderModel(
    var type: String = "TEXT",
    var text: String = "",
    var image: String = "",
    var isBold: Boolean = false,
    var alignment: String = "CENTER",
    var divider: String = ""
)