package Consumer

import AdapterClass.PaymentViewModel
import HelperClass.orderhelper
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.agrogo.R
import com.example.agrogo.databinding.UserDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener

class UserDashBoard : AppCompatActivity(),PaymentResultListener{
    lateinit var binding : UserDashboardBinding
    private val paymentViewModel: PaymentViewModel by viewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this is used for doing the fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding=DataBindingUtil.setContentView(this,R.layout.user_dashboard)
        supportFragmentManager.beginTransaction()
            .replace(R.id.userdash_view,UserHomeFragment())
            .commit()
        binding.userBottomnav.setOnItemSelectedListener{ item ->
            val selectedFragment = when (item.itemId){
                R.id.nv_home -> UserHomeFragment()
                R.id.nv_cart ->UserAdd2CartFragment()
                R.id.nv_prof -> UserProfileFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                .replace(R.id.userdash_view,it)
                .commit() }
            true
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        paymentViewModel.setPaymentResult(true, p0)

        paymentViewModel.selectedProduct.value?.let { product ->

            val order = AdapterClass.convertToOrderHelper(
                product = product,
                buyerUid = FirebaseAuth.getInstance().uid ?: "",
                paymentId = p0.toString()
            )
            AdapterClass.orderrepo.saveOrder(order) { success, msg ->
                if (success)
                    Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Failed to save order: $msg", Toast.LENGTH_SHORT).show()
            }

    }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        paymentViewModel.setPaymentResult(false, p1)
    }
    override fun onDestroy() {
        super.onDestroy()
        try {
            val checkout = Checkout()
            checkout.onDestroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}




