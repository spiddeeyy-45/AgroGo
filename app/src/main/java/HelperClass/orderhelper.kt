package HelperClass

data class orderhelper(
    var id : String? =null,
    val imageUrl: String = "",
    val buyerUid: String = "",
    val farmerUID : String?=null,
    val productId: String = "",
    val productName: String = "",
    val qty: Long = 0,
    val totalAmount: Double = 0.0,
    val paymentId: String = "",
    val status: String = "paid",           // initial status
    val verifiedByServer: Boolean = false, // change to true only after server webhook
    val timestamp: Long?=null
)
