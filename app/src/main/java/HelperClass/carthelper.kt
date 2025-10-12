package HelperClass

data class carthelper(
    val farmerUID : String?=null,
    var id :String ?=null,
    val category: String = "",
    val name: String = "",
    val description: String = "",
    val quantity: Double? = null,
    val price: Double? = null,
    val imageUrl: String = "",
    val timestamp: Long?=null
)
