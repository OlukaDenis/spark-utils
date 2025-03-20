package com.mcdenny.sparkutils.models

data class ReceiptSectionModel(
    var name: String = "",
    var line: String = "",
    var position: Int = 0,
    var items: List<ReceiptItemModel> = emptyList()
)