package com.example.agrogo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class seedcat : AppCompatActivity() {
    lateinit var backbtns: ImageView
    lateinit var searchEditTexts: EditText
    lateinit var searchIcons: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_seedcat)
        backbtns=findViewById(R.id.backbtnseed)
        searchEditTexts=findViewById(R.id.searchEditTextseed)
        searchIcons=findViewById(R.id.searchiconseed)
        //backbtn will go back to profile page
        backbtns.setOnClickListener(){
            val intent = Intent(this, Profile1::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger searchbar by clicking the icon image and redirect to the next page where we can search
        searchIcons.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger searchbar by clicking the textbox and redirect to the next page where we can search
        searchEditTexts.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }


    }
}