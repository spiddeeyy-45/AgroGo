import AdapterClass.OrderAdapter
import HelperClass.farmerproductdataclass
import HelperClass.orderhelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.databinding.FragmentOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderFragment : Fragment() {
    private lateinit var bindOrderFragment:FragmentOrderBinding
    private val binding get() = bindOrderFragment
    private lateinit var adapter : OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindOrderFragment= FragmentOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val farmerUid =FirebaseAuth.getInstance().currentUser?.uid
        if (farmerUid == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }
        val firestore = FirebaseFirestore.getInstance()
        val productList = mutableListOf<orderhelper>()
        val adapter = OrderAdapter(productList)
        bindOrderFragment.analyticsoreder.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        bindOrderFragment.analyticsoreder.adapter=adapter
        firestore
            .collectionGroup("orders")
            .get()
            .addOnSuccessListener {
                    documents ->
                productList.clear()
                for (doc in documents) {
                    var productid = doc.toObject(orderhelper::class.java)
                    productList.add(productid)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error loading products", Toast.LENGTH_SHORT).show()

            }


    }
}
