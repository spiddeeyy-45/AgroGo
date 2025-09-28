package CommonUI

import AdapterClass.PaymentViewModel
import AdapterClass.mostordedre
import Consumer.UserHomeFragment
import HelperClass.remHelper
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.R
import com.example.agrogo.databinding.GrainFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import org.json.JSONObject


class grain_fragment : Fragment() {
    private lateinit var Binding:GrainFragmentBinding
    private lateinit var GrainRecycler: mostordedre
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid  // current logged-in user
    private var email:String?=null
    private var phoneno:String?=null
    private var secret_key:String? =null
    private val paymentViewModel: PaymentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding=GrainFragmentBinding.inflate(inflater,container,false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email=requireActivity().intent.getStringExtra("email")
        phoneno=requireActivity().intent.getStringExtra("phoneno")
        secret_key=requireActivity().getString(R.string.key_id)
        // payment Sucess notify
        paymentViewModel.paymentResult.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { (success, message) ->
                if (success) {
                    Toast.makeText(requireContext(), "Payment Successful: $message", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Payment Failed: $message", Toast.LENGTH_LONG).show()
                }
            }
        }
        //backbtn handler
        Binding.backbtn.setOnClickListener {
            val HomeFragment=UserHomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.userdash_view,HomeFragment)
                .addToBackStack(null)
                .commit()
        }
        grainRecyclerHandler()
    }
    private fun grainRecyclerHandler(){
        val GrainList = mutableListOf<remHelper>()
        GrainRecycler = mostordedre(
            GrainList,
            onBuyNow = { product -> paymentViewModel.setSelectedProduct(product)
                buyNow(product)},
            onAddToCart = { product -> addToCart(product)}
        )
        Binding.grainrecycler.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        Binding.grainrecycler.adapter=GrainRecycler
        firestore.collectionGroup("products")
            .whereEqualTo("category","Grains")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val product = doc.toObject(remHelper::class.java)
                    product.id = doc.id
                    GrainList.add(product)
                }
                GrainRecycler.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Fetching failed: ${exception.message}")
            }
    }
    private fun addToCart(product: remHelper) {
        if (userId == null) {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
            return
        }
        firestore.collection("consumers")
            .document(userId)
            .collection("cart")
            .add(product)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show()
            }
    }
    private fun buyNow(product: remHelper) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key) // Razorpay Key ID


            // create order details
            val options = JSONObject()
            options.put("name", "AgroGo") // business/app name
            options.put("description", product.name)
            options.put("currency", "INR")

            // Razorpay expects price in PAISE (multiply by 100)
            val price = product.price ?: 0.0
            val quantity = product.quantity ?: 1
            val amount = price*quantity.toDouble()*100
            options.put("amount", amount)

            val prefill = JSONObject()
            prefill.put("email", email?:"")  // can get from Firebase user
            prefill.put("contact", phoneno?:"")

            options.put("prefill", prefill)

            // open razorpay checkout
            checkout.open(requireActivity(), options)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }



}