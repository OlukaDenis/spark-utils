package com.mcdenny.sparkutils.models

data class ReceiptSectionModel(
  var name: String = "",
   var line: String = "",
  var position: Int = 0,
  var items: List<ReceiptItemModel> = emptyList()
) {
    constructor(name: String, line: String, position: Int) : this(name, line, position, emptyList())
}