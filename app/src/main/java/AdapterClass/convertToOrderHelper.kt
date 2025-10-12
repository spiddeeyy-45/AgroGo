package AdapterClass

import HelperClass.ReHelper
import HelperClass.carthelper
import HelperClass.orderhelper
import HelperClass.recentlyorder
import HelperClass.remHelper

fun convertToOrderHelper (product: Any, buyerUid: String, paymentId: String): orderhelper {
    return when (product) {
        is remHelper -> orderhelper(
            farmerUID = product.farmerUID,
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
            farmerUID = product.farmerUID,
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

        is carthelper -> orderhelper(
            imageUrl = product.imageUrl,
            farmerUID = product.farmerUID,
            buyerUid = buyerUid,
            productId = product.id ?: "",
            productName = product.name ?: "",
            qty = (product.quantity ?: 1).toLong(),
            totalAmount = product.price ?: 0.0,
            paymentId = paymentId,
            status = "paid",
            timestamp = System.currentTimeMillis()
        )
        is recentlyorder -> orderhelper(
            farmerUID = product.farmerUID,
            imageUrl = product.imageUrl,
            buyerUid = buyerUid,
            productId = product.id ?:"",
            productName = product.name ?:"",
            qty = (product.quantity ?:1).toLong(),
            totalAmount = product.price?:0.0,
            paymentId = paymentId,
            status = "paid",
            timestamp = System.currentTimeMillis()

        )

        else -> throw IllegalArgumentException("Unknown product type")
    }
}