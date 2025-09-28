package Consumer

import CommonUI.LoginScreen
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrogo.R
import com.example.agrogo.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth

class UserProfileFragment : Fragment() {
    lateinit var Binding : FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding= FragmentUserProfileBinding.inflate(inflater,container,false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Binding.logoutBTN.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(requireContext(),LoginScreen ::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}