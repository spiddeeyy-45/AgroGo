import CommonUI.LoginScreen
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    lateinit var Binding : FragmentProfileBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        Binding=FragmentProfileBinding.inflate(inflater,container,false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Binding.logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(),LoginScreen::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        loadfarmerDetails()

    }
    private fun loadfarmerDetails() {
        if (userId == null) return
        firestore.collection("farmers").document(userId).get()
            .addOnSuccessListener { document ->
                Binding.farmerName.text = document.getString("firstname") ?: "User Name"
                Binding.farmerEmail.text = document.getString("email") ?: "user@example.com"
                Binding.farmerPhone.text = document.getString("phoneno") ?: "+91-XXXXXXXXXX"
                // Optional: load profile image if you store it
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load user details", Toast.LENGTH_SHORT).show()
            }
    }
}
