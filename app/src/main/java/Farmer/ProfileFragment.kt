import CommonUI.LoginScreen
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    lateinit var Binding : FragmentProfileBinding
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
    }
}
