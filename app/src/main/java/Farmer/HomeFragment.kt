import AdapterClass.FarmerHomeAdapter
import HelperClass.farmerproductdataclass
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var bindHomefragment: FragmentHomeBinding
    private lateinit var adapter: FarmerHomeAdapter
    private val binding get() = bindHomefragment
    private val firestore = FirebaseFirestore.getInstance()
    private val farmer = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindHomefragment = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore.collection("farmers")
            .document(farmer!!).get()
            .addOnSuccessListener { document ->
                binding.name.text = document.getString("firstname") ?: "User Name"
            }
        if (farmer == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val productList = mutableListOf<farmerproductdataclass>()
         adapter = FarmerHomeAdapter(
            productList,
            onEditClick = { product ->
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.editproductlayout, null)

                // find all input fields
                val categoryInput = dialogView.findViewById<TextInputEditText>(R.id.category_name)
                val nameInput = dialogView.findViewById<TextInputEditText>(R.id.product_name)
                val descriptionInput = dialogView.findViewById<TextInputEditText>(R.id.product_description)
                val quantityInput = dialogView.findViewById<TextInputEditText>(R.id.product_quantity)
                val priceInput = dialogView.findViewById<TextInputEditText>(R.id.product_price)

                // pre-fill with current product data
                categoryInput.setText(product.category)
                nameInput.setText(product.name)
                descriptionInput.setText(product.description)
                quantityInput.setText(product.quantity?.toString())
                priceInput.setText(product.price?.toString())

                // show dialog
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .setTitle("Edit Product")
                    .setPositiveButton("Update", null)  // we'll override later
                    .setNegativeButton("Cancel", null)
                    .create()

                dialog.show()

                // Override "Update" button to validate before closing
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val updatedCategory = categoryInput.text.toString().trim()
                    val updatedName = nameInput.text.toString().trim()
                    val updatedDesc = descriptionInput.text.toString().trim()
                    val updatedQty = quantityInput.text.toString().trim()
                    val updatedPrice = priceInput.text.toString().trim()

                    if (updatedName.isEmpty() || updatedCategory.isEmpty() || updatedPrice.isEmpty() || updatedQty.isEmpty()) {
                        Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val quantity = updatedQty.toIntOrNull()
                    val price = updatedPrice.toDoubleOrNull()

                    if (quantity == null || price == null) {
                        Toast.makeText(requireContext(), "Invalid number format", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    // Call Firestore update
                    updateProduct(
                        productId = product.id!!,
                        category = updatedCategory,
                        name = updatedName,
                        description = updatedDesc,
                        quantity = quantity,
                        price = price
                    )

                    dialog.dismiss()
                }

            },
            onDeleteClick = { product ->
                product.id?.let { docId ->
                    firestore.collection("farmers")
                        .document(farmer)
                        .collection("products")
                        .document(docId)
                        .delete()
                        .addOnSuccessListener {
                            productList.remove(product)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
        binding.LivestockHandler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.LivestockHandler.adapter = adapter

        firestore.collection("farmers")
            .document(farmer)
            .collection("products")
            .get()
            .addOnSuccessListener { documents ->
                productList.clear()
                for (doc in documents) {
                    var productid = doc.toObject(farmerproductdataclass::class.java)
                    productid.id = doc.id     // store document ID for delete/update
                    productList.add(productid)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error loading products", Toast.LENGTH_SHORT).show()
            }
    }
    private fun updateProduct(
        productId: String,
        category: String,
        name: String,
        description: String,
        quantity: Int,
        price: Double
    ) {
        val updates = mapOf(
            "category" to category,
            "name" to name,
            "description" to description,
            "quantity" to quantity,
            "price" to price
        )

        firestore.collection("farmers")
            .document(farmer!!)
            .collection("products")
            .document(productId)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Product updated successfully", Toast.LENGTH_SHORT).show()
                refreshProductList()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update product", Toast.LENGTH_SHORT).show()
            }
    }
    private fun refreshProductList() {
        firestore.collection("farmers")
            .document(farmer!!)
            .collection("products")
            .get()
            .addOnSuccessListener { documents ->
                val updatedList = mutableListOf<farmerproductdataclass>()
                for (doc in documents) {
                    val product = doc.toObject(farmerproductdataclass::class.java)
                    product.id = doc.id
                    updatedList.add(product)
                }
                adapter.updateList(updatedList) // we’ll add this function next
            }}

}
