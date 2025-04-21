package com.example.agrogo

import android.app.ActivityOptions
import android.util.Pair
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.view.View


class MainActivity : AppCompatActivity() {

    private var SPLASH_SCREEN: Int = 4000
    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation
    lateinit var imageviewlogo :ImageView
    lateinit var texlogo:TextView
    lateinit var texslogan:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
         topAnim = AnimationUtils.loadAnimation(this, R.anim.main_screeb)
         bottomAnim = AnimationUtils.loadAnimation(this, R.anim.main_screen2)
         imageviewlogo=findViewById(R.id.logo)
         texlogo=findViewById(R.id.logotext)
         texslogan=findViewById(R.id.slogan)
        imageviewlogo.setAnimation(topAnim)
        texlogo.setAnimation(bottomAnim)
        texslogan.setAnimation(bottomAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, DashBoard::class.java)
            val pairs = arrayOfNulls<Pair<View, String>>(2)
            pairs[0] = Pair(imageviewlogo, "logoo")
            pairs[1] = Pair(texlogo, "logot")
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
            startActivity(intent,options.toBundle()) }, SPLASH_SCREEN.toLong())

    }
}