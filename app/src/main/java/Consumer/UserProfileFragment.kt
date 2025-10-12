package Consumer

import CommonUI.LoginScreen
import AdapterClass.OrderAdapter
import HelperClass.orderhelper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrogo.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var orderAdapter: OrderAdapter
    private val orderList = mutableListOf<orderhelper>()
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logout functionality
        binding.logoutBTN.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginScreen::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Load user details
        loadUserDetails()

        // Setup RecyclerView
        orderAdapter = OrderAdapter(orderList)
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersRecyclerView.adapter = orderAdapter

        // Load user orders
        loadUserOrders()
    }

    private fun loadUserDetails() {
        if (userId == null) return
        firestore.collection("consumers").document(userId).get()
            .addOnSuccessListener { document ->
                binding.userName.text = document.getString("firstname") ?: "User Name"
                binding.userEmail.text = document.getString("email") ?: "user@example.com"
                binding.userPhone.text = document.getString("phoneno") ?: "+91-XXXXXXXXXX"
                // Optional: load profile image if you store it
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load user details", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserOrders() {
        if (userId == null) return
        firestore.collection("consumers")
            .document(userId)
            .collection("orders")
            .get()
            .addOnSuccessListener { snapshot ->
                orderList.clear()
                for (doc in snapshot.documents) {
                    val order = doc.toObject(orderhelper::class.java)
                    order?.id = doc.id
                    if (order != null) orderList.add(order)
                }
                orderAdapter.notifyDataSetChanged()

                // Show empty orders placeholder if no orders
                binding.emptyOrdersLayout.visibility = if (orderList.isEmpty()) View.VISIBLE else View.GONE
                binding.ordersRecyclerView.visibility = if (orderList.isEmpty()) View.GONE else View.VISIBLE
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show()
            }
    }
}
