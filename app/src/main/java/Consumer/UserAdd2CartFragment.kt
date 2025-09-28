package Consumer

import AdapterClass.CartAdapter
import AdapterClass.PaymentViewModel
import HelperClass.carthelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentUserAdd2CartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import org.json.JSONObject

class UserAdd2CartFragment : Fragment() {
    private lateinit var Binding:FragmentUserAdd2CartBinding
    private lateinit var cartAdapter: CartAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private var TotalPrice : Double =0.0
    private val userId = FirebaseAuth.getInstance().currentUser?.uid  // current logged-in user
    private var email:String?=null
    private var phoneno:String?=null
    private var secret_key:String? =null
    val productList = mutableListOf<carthelper>()
    private val paymentViewModel: PaymentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentUserAdd2CartBinding.inflate(inflater,container,false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerhandler()
        secret_key=getString(R.string.key_id)
        // payment Sucess notify
        paymentViewModel.paymentResult.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { (success, message) ->
                if (success) {
                    Toast.makeText(requireContext(), "Payment Successful: $message", Toast.LENGTH_LONG).show()
                    clearCartFromFirestore()
                } else {
                    Toast.makeText(requireContext(), "Payment Failed: $message", Toast.LENGTH_LONG).show()
                }
            }
        }
        //buy All btn
        Binding.buyallBtn.setOnClickListener{
            if (productList.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                buyAllProducts()
            }
        }

    }
    private fun recyclerhandler(){
        if(userId == null){
            Toast.makeText(requireContext(),"User Not Logged In ! ",Toast.LENGTH_SHORT).show()
            return
        }

        cartAdapter = CartAdapter(
            productList,
            onBuyBTN = { product -> paymentViewModel.setSelectedProduct(product)
                buyBTN(product)},
            onRemoveBTN = { product ->
                product.id?.let { docId ->
                    firestore.collection("consumers")
                        .document(userId)
                        .collection("cart")
                        .document(docId)
                        .delete()
                        .addOnSuccessListener {
                            productList.remove(product)
                                cartAdapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(),"Product Removed From Cart",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
        Binding.cartRecycler.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        Binding.cartRecycler.adapter=cartAdapter
        firestore.collection("consumers")
            .document(userId)
            .collection("cart")
            .get()
            .addOnSuccessListener { documents ->
                productList.clear()
                for (doc in documents) {
                    var productid = doc.toObject(carthelper::class.java)
                    productid.id = doc.id
                    val price =productid.price?:0.0
                    val qty = productid.quantity?:1
                    TotalPrice +=price * qty .toDouble()
                    productList.add(productid)
                }
                cartAdapter.notifyDataSetChanged()
                Binding.totalPrice.text="$TotalPrice"
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Failed To Fetching Your Cart Item ",Toast.LENGTH_SHORT).show()
            }

    }
    private fun buyBTN(product:carthelper){
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key) //   Razorpay Key ID


            // create order details
            val options = JSONObject()
            options.put("name", "AgroGo") // app name
            options.put("description", product.name)
            options.put("currency", "INR")

            // Razorpay expects price in PAISE (multiply by 100)
            val price = product.price ?: 0.0
            val quantity = product.quantity ?: 1
            val amount = price*quantity.toDouble()*100
            options.put("amount", amount)

            val prefill = JSONObject()
            prefill.put("email", email?:"")  //  get from Firebase user
            prefill.put("contact", phoneno?:"")

            options.put("prefill", prefill)

            // open razorpay checkout
            checkout.open(requireActivity(), options)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: " + e.message, Toast.LENGTH_LONG).show()
        }

    }
    private fun buyAllProducts(){
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key) // Razorpay Key ID

            // Collect product names from your cart list
            val itemNames = productList.joinToString(", ") { it.name }

            val options = JSONObject()
            options.put("name", "AgroGo")
            options.put("description", "Buying: $itemNames")
            options.put("currency", "INR")
            options.put("amount", (TotalPrice * 100).toInt()) // in paise

            val prefill = JSONObject()
            prefill.put("email", email ?: "")
            prefill.put("contact", phoneno ?: "")
            options.put("prefill", prefill)

            checkout.open(requireActivity(), options)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun clearCartFromFirestore() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = FirebaseFirestore.getInstance()
            .collection("consumers")
            .document(userId)
            .collection("cart")

        cartRef.get()
            .addOnSuccessListener { snapshot ->
                val batch = FirebaseFirestore.getInstance().batch()
                for (doc in snapshot.documents) {
                    batch.delete(doc.reference)
                }
                batch.commit()
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Cart cleared!", Toast.LENGTH_SHORT).show()
                        productList.clear()
                        cartAdapter.notifyDataSetChanged()
                        TotalPrice = 0.0
                        Binding.totalPrice.text="0.0"
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to clear cart", Toast.LENGTH_SHORT).show()
                    }
            }
    }


}