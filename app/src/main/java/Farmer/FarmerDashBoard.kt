package Farmer

import OrderFragment
import ProfileFragment
import AddItemFragment
import HomeFragment
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.agrogo.R
import com.example.agrogo.databinding.FarmerDashBoardBinding

class FarmerDashBoard : AppCompatActivity() {
    lateinit var binding3:FarmerDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding3=DataBindingUtil.setContentView(this,R.layout.farmer_dash_board)
        // Load default fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        binding3.bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_additem -> AddItemFragment()
                R.id.nav_Order -> OrderFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_home -> HomeFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }


    }
}