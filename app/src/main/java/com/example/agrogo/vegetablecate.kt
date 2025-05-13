package com.example.agrogo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vegf2Helper
import vf2adapter
import vmostadapter

class vegetablecate : AppCompatActivity() {
    lateinit var backbtnv: ImageView
    lateinit var searchEditTextv: EditText
    lateinit var searchIconv: ImageView
    lateinit var vf2ad:vf2adapter
    lateinit var vmosad:vmostadapter
    lateinit var vf2:RecyclerView
    lateinit var vegmo:RecyclerView
    companion object{ lateinit var vf2item: ArrayList<vegf2Helper> }
    lateinit var vmitem : ArrayList<vegmost>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this is used for doing the fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_vegetablecate)
        backbtnv=findViewById(R.id.backbtnveg)
        searchEditTextv=findViewById(R.id.searchEditTextveg)
        searchIconv=findViewById(R.id.searchiconveg)
        vf2=findViewById(R.id.vegfeaturedrecycler12)
        vegmo=findViewById(R.id.movegrecy)
        backbtn()
        searchedi()
        searchedte()
        vegsetupFeatured2RecyclerView()
        vegsetupmostorderedRecyclerView()
    }
    private fun backbtn(){
        //backbtn will go back to profile page
        backbtnv.setOnClickListener(){
            val intent = Intent(this, Profile1::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun searchedi(){
        //this will trigger searchbar by clicking the textbox and redirect to the next page where we can search
        searchEditTextv.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun searchedte(){
        //this will trigger searchbar by clicking the icon image and redirect to the next page where we can search
        searchIconv.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun vegsetupFeatured2RecyclerView() {
        vf2.setHasFixedSize(true)
        vf2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        vf2item = arrayListOf(
            vegf2Helper(R.drawable.vegpotato, "Potato", "Nutrient-rich and filling."),
            vegf2Helper(R.drawable.carr, "Carrot", "Boosts vision, skin, and immunity."),
            vegf2Helper(R.drawable.vegcucu, "Cucumber", "Hydrates and aids digestion."),
            vegf2Helper(R.drawable.veggarlic, "Garlic", "Strengthens immunity and heart."),
            vegf2Helper(R.drawable.vegpotato, "Potato", "Energizes and supports heart health."),
            vegf2Helper(R.drawable.vegtomato, "Tomato", "Rich in antioxidants and heart-friendly."),
            vegf2Helper(R.drawable.vegbellpepper, "Bell Pepper", "High in vitamins, boosts immunity."),
            vegf2Helper(R.drawable.vegcauli, "Cauliflower", "Good for digestion and detox."),
            vegf2Helper(R.drawable.vegonion, "Onion", "Supports heart and immunity."),
            vegf2Helper(R.drawable.carr, "Carrot", "Good for eyes and skin."),
            vegf2Helper(R.drawable.vegcucu, "Cucumber", "Cooling and fiber-rich."),
            vegf2Helper(R.drawable.vegtomato, "Tomato", "Lycopene-rich and skin-friendly."),
            vegf2Helper(R.drawable.vegeggplnt, "Eggplant", "Heart-healthy and antioxidant-rich."),
            vegf2Helper(R.drawable.vegtomato, "Tomato", "Boosts skin and heart health.")
        )
        vf2ad = vf2adapter(vf2item)
        vf2.adapter = vf2ad
    }
    private fun vegsetupmostorderedRecyclerView() {
        vegmo.setHasFixedSize(true)
        vegmo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        vmitem = arrayListOf(
            vegmost(R.drawable.vegcucu, "Cucumber", "Hydrates and aids digestion."),
            vegmost(R.drawable.veggarlic, "Garlic", "Strengthens immunity and heart."),
            vegmost(R.drawable.vegpotato, "Potato", "Energizes and supports heart health."),
            vegmost(R.drawable.vegtomato, "Tomato", "Rich in antioxidants and heart-friendly."),
            vegmost(R.drawable.carr, "Carrot", "Boosts vision, skin, and immunity."),
            vegmost(R.drawable.vegbellpepper, "Bell Pepper", "High in vitamins, boosts immunity."),
            vegmost(R.drawable.vegcauli, "Cauliflower", "Good for digestion and detox."),
            vegmost(R.drawable.vegonion, "Onion", "Supports heart and immunity."),
            vegmost(R.drawable.vegeggplnt, "Eggplant", "Heart-healthy and antioxidant-rich."),
            )

        vmosad = vmostadapter(vmitem)
        vegmo.adapter = vmosad
    }
}