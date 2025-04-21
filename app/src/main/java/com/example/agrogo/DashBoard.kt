package com.example.agrogo

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class DashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var newuser1:Button
        lateinit var logo1:ImageView
        lateinit var logotext:TextView
        lateinit var slogantext:TextView
        lateinit var  pass1:TextInputLayout
        lateinit var  user1:TextInputLayout
        lateinit var  go1:Button


        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_dash_board)
        newuser1 = findViewById(R.id.newacc)
        logotext=findViewById(R.id.logotext1)
        slogantext=findViewById(R.id.slogan_name)
        logo1=findViewById(R.id.logo_Image)
        pass1=findViewById(R.id.password)
        user1=findViewById(R.id.user_name)
        go1=findViewById(R.id.go)
        newuser1.setOnClickListener(){
            val intent = Intent(this, newuser::class.java)
            val pairs = arrayOfNulls<Pair<View, String>>(7)
            pairs[0] = Pair(logo1, "logoo")
            pairs[1] = Pair(logotext, "logot")
            pairs[2] = Pair(slogantext, "logot")
            pairs[3] = Pair(pass1, "pass_tran")
            pairs[4] = Pair(user1, "user_tran")
            pairs[5] = Pair(go1, "go_tran")
            pairs[6] = Pair(newuser1, "newu_tran")
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
            startActivity(intent,options.toBundle())
        }

    }
    }
