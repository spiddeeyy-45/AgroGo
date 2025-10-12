package AdapterClass

import HelperClass.orderhelper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object orderrepo {
    private val db = FirebaseFirestore.getInstance()

    fun saveOrder(
        order: orderhelper,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val user =FirebaseAuth.getInstance().currentUser?.uid
        if (user == null) {
            onComplete(false, "User not logged in")
            return
        }
        db.collection("consumers")
            .document(user)
            .collection("orders")
            .add(order)
            .addOnSuccessListener { docRef ->
                val orderId = docRef.id

                //  Save in farmer collection using FarmerUID from order
                val farmerUid = order.farmerUID
                if (!farmerUid.isNullOrEmpty()) {
                    db.collection("farmers")
                        .document(farmerUid)
                        .collection("orders")
                        .document(orderId)   // use same ID for consistency
                        .set(order)
                }
                onComplete(true, orderId)
            }.addOnSuccessListener { Log.d("ORDER_DEBUG", "Order saved under farmer successfully") }
            .addOnFailureListener { e -> Log.e("ORDER_DEBUG", "Failed to save order under farmer: ${e.message}") }
    }
}