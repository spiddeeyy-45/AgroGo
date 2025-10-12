package Consumer

import AdapterClass.PaymentViewModel
import AdapterClass.RecentlyAdapter
import AdapterClass.adapterre
import AdapterClass.hoardingSlide
import AdapterClass.mostordedre
import CommonUI.fruits_fragment
import CommonUI.grain_fragment
import CommonUI.search_fragment
import CommonUI.vegetable_fragment
import HelperClass.ReHelper
import HelperClass.hoardingHelper
import HelperClass.recentlyorder
import HelperClass.remHelper
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.razorpay.Checkout
import org.json.JSONObject

class UserHomeFragment : Fragment()  {
    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var featuredadapter: adapterre
    private lateinit var mostorderAdapter:mostordedre
    private lateinit var recentlyAdapter: RecentlyAdapter
    private lateinit var helper: List<hoardingHelper>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid  // current logged-in user
    private var email:String?=null
    private var phoneno:String?=null
    private var secret_key:String? =null
    private val paymentViewModel: PaymentViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email=requireActivity().intent.getStringExtra("email")
        phoneno=requireActivity().intent.getStringExtra("phoneno")
        secret_key = requireActivity().getString(R.string.key_id)
        //  Setup hoarding/offer slider
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.offerHoarding.currentItem
                val nextItem = (currentItem + 1) % helper.size
                binding.offerHoarding.currentItem = nextItem
                handler.postDelayed(this, 4000)
            }
        }
        handler.postDelayed(runnable, 4000)
        sliderecyeler()
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

        // 🔹 Initialize adapter with button actions
        val productList = mutableListOf<ReHelper>()
        featuredadapter = adapterre(
            productList,
            onAddToCart = { product -> addToCart(product) },
            onBuyNow = { product -> paymentViewModel.setSelectedProduct(product)
                buyNow(product) }
        )

        binding.featuredItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.featuredItem.adapter = featuredadapter

        //  Fetch products from Firestore
        firestore.collectionGroup("products")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val product = doc.toObject(ReHelper::class.java)
                    product.id = doc.id
                    productList.add(product)
                }
                featuredadapter.notifyDataSetChanged()
            }
        mostorderedRecycler()
        recentlyorderedRecycler()

        //button for fruits and vegtable and grains
        binding.fruitIcon.setOnClickListener {
            val newfragment = fruits_fragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.userdash_view,newfragment)
                .addToBackStack(null)
                .commit()
        }
        binding.vegIcon.setOnClickListener{
            val vegFragment =  vegetable_fragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.userdash_view,vegFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.grainIcon.setOnClickListener{
            val grainfragment=grain_fragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.userdash_view,grainfragment)
                .addToBackStack(null)
                .commit()
        }
        //search view
        binding.searchIcon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    val searchFragment = search_fragment().apply {
                        arguments = Bundle().apply {
                            putString("search_query", query)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.userdash_view, searchFragment)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


    }


    //  Add product to "cart" collection in Firestore
    private fun addToCart(product: ReHelper) {
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
    private fun buyNow(product: ReHelper) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key) // 🔑 Your Razorpay Key ID


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
    private fun addToCart2(product: remHelper) {
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
    private fun buyNow2(product: remHelper) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key) // 🔑 Your Razorpay Key ID


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
    private fun sliderecyeler() {
        helper = listOf(
            hoardingHelper(R.raw.offer),
            hoardingHelper(R.raw.ecomerce),
            hoardingHelper(R.raw.eid_mubarak),
            hoardingHelper(R.raw.ramadan)
        )
        val adapter = hoardingSlide(helper)
        binding.offerHoarding.adapter = adapter
    }
    private fun mostorderedRecycler(){

        val productList2 = mutableListOf<remHelper>()
        mostorderAdapter = mostordedre(
            productList2,
            onAddToCart = { product -> addToCart2(product) },
            onBuyNow = { product -> paymentViewModel.setSelectedProduct(product)
                buyNow2(product) }
        )

        binding.mostRecy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mostRecy.adapter = mostorderAdapter

        //  Fetch products from Firestore
        firestore.collectionGroup("products")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val product2 = doc.toObject(remHelper::class.java)
                    product2.id = doc.id
                    productList2.add(product2)
                }
                mostorderAdapter.notifyDataSetChanged()
            }
    }
    private fun recentlyorderedRecycler(){
        val productList = mutableListOf<recentlyorder>()
        recentlyAdapter = RecentlyAdapter(
            productList,
            onAddToCart3 = { product -> addToCart3(product)},
            onBuyNow3 = {product -> paymentViewModel.setSelectedProduct(product)
                buyNow3(product)}
        )
        binding.recentrecy.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recentrecy.adapter=recentlyAdapter
        firestore.collectionGroup("products")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    val productl = doc.toObject(recentlyorder::class.java)
                    productl.id = doc.id
                    productList.add(productl)
                }
                recentlyAdapter.notifyDataSetChanged()
            }
    }
    private fun buyNow3(product: recentlyorder) {
        try {
            val checkout = Checkout()
            checkout.setKeyID(secret_key)
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
    private fun addToCart3(product: recentlyorder) {
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


}
