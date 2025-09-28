package CommonUI

import Consumer.UserDashBoard
import Farmer.FarmerDashBoard
import android.app.ActivityOptions
import android.util.Pair
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.agrogo.R
import com.example.agrogo.databinding.SplashScreen1Binding


class Splashscreen1 : AppCompatActivity() {
    lateinit var binding :SplashScreen1Binding
    private var SPLASH_SCREEN: Int = 2000
    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding=DataBindingUtil.setContentView(this,R.layout.splash_screen1)
         //this are hooks for the animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.main_screeb)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.main_screen2)
         //this are binding logo and image with animation
         binding.slogantext.setAnimation(bottomAnim)
         binding.logo.setAnimation(topAnim)
         binding.logotext.setAnimation(bottomAnim)


         Handler(Looper.getMainLooper()).postDelayed({
             val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
             val onboardingSeen = prefs.getBoolean("onboarding_seen", false)
             val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
             val currentUser = auth.currentUser

             if (!onboardingSeen) {
                 // First time install → Onboarding
                 val intent = Intent(this, OnBoardingScreen::class.java)
                 goWithAnimation(intent)

             } else if (currentUser != null) {
                 // User logged in → check if they are in farmers or consumers collection
                 val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

                 // First check farmers collection
                 db.collection("farmers").document(currentUser.uid).get()
                     .addOnSuccessListener { farmerDoc ->
                         if (farmerDoc.exists()) {
                             //  Farmer found → Go to Farmer Dashboard
                             val intent = Intent(this, FarmerDashBoard::class.java)
                             goWithAnimation(intent)
                         } else {
                             // If not farmer → check consumer collection
                             db.collection("consumers").document(currentUser.uid).get()
                                 .addOnSuccessListener { consumerDoc ->
                                     if (consumerDoc.exists()) {
                                         //  Consumer found
                                         val intent = Intent(this, UserDashBoard::class.java)
                                         goWithAnimation(intent)
                                     } else {
                                         val intent = Intent(this, LoginScreen::class.java)
                                         goWithAnimation(intent)
                                     }
                                 }
                                 .addOnFailureListener {
                                     val intent = Intent(this, LoginScreen::class.java)
                                     goWithAnimation(intent)
                                 }
                         }
                     }
                     .addOnFailureListener {
                         val intent = Intent(this, LoginScreen::class.java)
                         goWithAnimation(intent)
                     }

             } else {
                 // Not logged in → go to Login
                 val intent = Intent(this, LoginScreen::class.java)
                 goWithAnimation(intent)
             }
         }, SPLASH_SCREEN.toLong())


     }
    private fun goWithAnimation(intent: Intent) {
        val pairs = arrayOfNulls<Pair<View, String>>(3)
        pairs[0] = Pair(binding.logo, "logoo")
        pairs[1] = Pair(binding.logotext, "logot")
        pairs[2] = Pair(binding.slogantext, "slogont")
        val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
        startActivity(intent, options.toBundle())
        finish()
    }

}