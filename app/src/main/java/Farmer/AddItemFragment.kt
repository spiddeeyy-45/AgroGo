import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AddItemFragment : Fragment() {

    private lateinit var bindingai: FragmentAddItemBinding
    private var selectedImageUri: Uri? = null

    //  Image picker launcher
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                bindingai.itemimage.setImageURI(it) // Show selected image
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingai = FragmentAddItemBinding.inflate(inflater, container, false)
        return bindingai.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingai.submitItem.setOnClickListener {
            val Productname = bindingai.productName.text.toString().trim()
            val category = bindingai.categoryName.text.toString().trim()
            val description = bindingai.productDescription.text.toString().trim()
            val quantity = bindingai.productQuantity.text.toString().trim()
            val pricePerkg = bindingai.productPrice.text.toString().trim()

            // Category validation
            if (category.isEmpty()) {
                bindingai.categoryName.error = "Category required"
                return@setOnClickListener
            } else if (!category.equals("Fruits", ignoreCase = true) &&
                !category.equals("Vegetables", ignoreCase = true) &&
                !category.equals("Grains", ignoreCase =true )
            ) {
                bindingai.categoryName.error = "Only 'Fruits , Grains and Vegetables' allowed"
                return@setOnClickListener
            } else {
                bindingai.categoryName.error = null
            }

            // Product name validation
            if (Productname.isEmpty()) {
                bindingai.productNameLayout.error = "Product name required"
                return@setOnClickListener
            } else {
                bindingai.productNameLayout.error = null
            }

            // Description validation
            if (description.isEmpty()) {
                bindingai.productDescriptionLayout.error = "Description required"
                return@setOnClickListener
            } else {
                bindingai.productDescriptionLayout.error = null
            }

            if (description.split("\\s+".toRegex()).size > 10) {
                bindingai.productDescriptionLayout.error = "Description can't exceed 10 words"
                return@setOnClickListener
            } else {
                bindingai.productDescriptionLayout.error = null
            }

            // Quantity validation
            if (quantity.isEmpty()) {
                bindingai.productQuantityLayout.error = "Quantity required"
                return@setOnClickListener
            } else {
                bindingai.productQuantityLayout.error = null
            }

            // Price validation
            if (pricePerkg.isEmpty()) {
                bindingai.productPriceLayout.error = "Price required"
                return@setOnClickListener
            } else {
                bindingai.productPriceLayout.error = null
            }

            // Current Farmer UID
            val farmerUid = FirebaseAuth.getInstance().currentUser?.uid
            if (farmerUid == null) {
                Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedImageUri == null) {
                Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase references - Use your specific far-away bucket here
            val storageRef = FirebaseStorage
                .getInstance("gs://agogo78906.firebasestorage.app")
                .reference

            val firestore = FirebaseFirestore.getInstance()

            // Image reference
            val imageRef = storageRef.child("product_images/${System.currentTimeMillis()}.jpg")

            // Upload image
            imageRef.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { imageUrl ->

                        // Prepare product data
                        val product = hashMapOf(
                            "FarmerUID" to farmerUid,
                            "category" to category,
                            "name" to Productname,
                            "description" to description,
                            "quantity" to quantity.toDoubleOrNull(),
                            "price" to pricePerkg.toDoubleOrNull(),
                            "imageUrl" to imageUrl.toString(),
                            "timestamp" to System.currentTimeMillis()
                        )

                        // Save to Firestore under farmer's UID
                        Log.d("UPLOAD", "Saving product for UID: $farmerUid")
                        firestore.collection("farmers")
                            .document(farmerUid)
                            .collection("products")
                            .add(product)
                            .addOnSuccessListener {
                                Log.d("UPLOAD", "Uploading product: $product")
                                Toast.makeText(requireContext(), "Product uploaded successfully!", Toast.LENGTH_SHORT).show()
                                //this will clear the blocks after successful submit
                                bindingai.productName.text?.clear()
                                bindingai.categoryName.text?.clear()
                                bindingai.productDescription.text?.clear()
                                bindingai.productQuantity.text?.clear()
                                bindingai.productPrice.text?.clear()
                                bindingai.itemimage.setImageResource(R.drawable.addphoto)
                                selectedImageUri = null
                            }
                            .addOnFailureListener { e ->
                                Log.e("UPLOAD", "Error saving product data", e)
                                Toast.makeText(requireContext(), "Error saving product data", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("UPLOAD", "Image upload failed", e)
                    Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                }
        }

        bindingai.itemimage.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }
}
