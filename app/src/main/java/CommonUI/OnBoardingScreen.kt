package CommonUI

import AdapterClass.onboardslideadapter
import HelperClass.onboardslide
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.agrogo.R
import com.example.agrogo.databinding.OnBoardingScreenBinding



class OnBoardingScreen : AppCompatActivity() {
    lateinit var binding1: OnBoardingScreenBinding
    lateinit var slideadapter:onboardslideadapter
    lateinit var dot: Array<TextView>
    lateinit var onboardslidehelper: List<onboardslide>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding1 = DataBindingUtil.setContentView(this, R.layout.on_boarding_screen)
        sliderecyeler()
        dot = Array(3){ TextView(this) }
        dotfun(0)
        // this will return onpageselected which will be indicated by the dotfun
        binding1.onboardimages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dotfun(position)  // Call your dot update function here
            }
        })
        //this will handle the auto slide as well idicator
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                val currentItem = binding1.onboardimages.currentItem
                val nextItem = (currentItem + 1) % onboardslidehelper.size
                binding1.onboardimages.currentItem = nextItem
                handler.postDelayed(this, 2000)
            }
        }
        handler.postDelayed(runnable, 2000)
        //this function will launch and go to the loginscreen
        letgetstartedfun()
        nexticon()


    }
    private fun sliderecyeler(){
        binding1.onboardimages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        onboardslidehelper= listOf(
            onboardslide(R.raw.delivery,
                "Through out the country",
                "Easily deliver your produce to buyers across the nation with complete tracking and timely updates. Reach more markets, faster and smarter."),
            onboardslide(R.raw.farmingwithanalytic,
                "Soil to Sale — Farmer’s Profit",
                "A new era for agriculture begins here.Our platform empowers farmers to sell their produce directly to buyers, eliminating unnecessary middlemen and ensuring that every rupee goes where it belongs — to the farmer."),
            onboardslide(R.raw.planting,
            "Smart Planting, Smarter Profits",
            "Plan your crop cycle with data-driven insights and smart tools. Increase yield, reduce waste, and grow with confidence every season")
        )
        val adapter = onboardslideadapter(onboardslidehelper)
        binding1.onboardimages.adapter = adapter
    }
    private fun dotfun(position:Int ){
        binding1.dots.removeAllViews()
        for (i in dot.indices) {
            dot[i] = TextView(this).apply {
                text = "•"
                textSize = 35f
                setTextColor(Color.GRAY)
            }
            binding1.dots.addView(dot[i])
        }
        if (position in dot.indices)
        {
            dot[position].setTextColor(ContextCompat.getColor(this, R.color.green_500))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)}
    private fun letgetstartedfun(){
        binding1.getstartedbutton.setOnClickListener(){
            //  Save onboarding seen
            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            prefs.edit().putBoolean("onboarding_seen", true).apply()

            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun nexticon(){
        binding1.nexticon.setOnClickListener(){
            //  Save onboarding seen
            val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            prefs.edit().putBoolean("onboarding_seen", true).apply()

            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

}