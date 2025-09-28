import AdapterClass.FarmerHomeAdapter
import HelperClass.farmerproductdataclass
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var bindHomefragment: FragmentHomeBinding
    private lateinit var adapter: FarmerHomeAdapter
    private val binding get() = bindHomefragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindHomefragment = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.text = requireActivity().intent.getStringExtra("name")
        binding.address.text = "Mira Road"


        val farmerUid = FirebaseAuth.getInstance().currentUser?.uid
        if (farmerUid == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val firestore = FirebaseFirestore.getInstance()
        val productList = mutableListOf<farmerproductdataclass>()
         adapter = FarmerHomeAdapter(
            productList,
            onEditClick = { product ->
                Toast.makeText(requireContext(), "Edit ${product.name}", Toast.LENGTH_SHORT).show()

            },
            onDeleteClick = { product ->
                product.id?.let { docId ->
                    firestore.collection("farmers")
                        .document(farmerUid)
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
            .document(farmerUid)
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
}
