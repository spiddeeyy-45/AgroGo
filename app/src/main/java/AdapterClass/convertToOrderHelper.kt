package AdapterClass

import HelperClass.ReHelper
import HelperClass.carthelper
import HelperClass.orderhelper
import HelperClass.remHelper

fun convertToOrderHelper (product: Any, buyerUid: String, paymentId: String): orderhelper {
    return when (product) {
        is remHelper -> orderhelper(
            FarmerUID = product.FarmerUID,
            imageUrl = product.imageUrl,
            buyerUid = buyerUid,
            productId = product.id ?: "",
            productName = product.name ?: "",
            qty = (product.quantity ?: 1).toLong(),
            totalAmount = product.price ?: 0.0,
            paymentId = paymentId,
            status = "paid",
            timestamp = System.currentTimeMillis()
        )

        is ReHelper -> orderhelper(
            imageUrl = product.imageUrl,
            FarmerUID = product.FarmerUID,
            buyerUid = buyerUid,
            productId = product.id ?: "",
            productName = product.name ?: "",
            qty = (product.quantity ?: 1).toLong(),
            totalAmount = product.price ?: 0.0,
            paymentId = paymentId,
            status = "paid",
            timestamp = System.currentTimeMillis()
        )

        is carthelper -> orderhelper(
            imageUrl = product.imageUrl,
            FarmerUID = product.FarmerUID,
            buyerUid = buyerUid,
            productId = product.id ?: "",
            productName = product.name ?: "",
            qty = (product.quantity ?: 1).toLong(),
            totalAmount = product.price ?: 0.0,
            paymentId = paymentId,
            status = "paid",
            timestamp = System.currentTimeMillis()
        )

        else -> throw IllegalArgumentException("Unknown product type")
    }
}