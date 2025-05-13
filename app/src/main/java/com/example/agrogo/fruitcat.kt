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
import frf1helper
import frmost

class fruitcat : AppCompatActivity() {
    lateinit var backbtn:ImageView
    lateinit var searchEditText: EditText
    lateinit var searchIcon:ImageView
    lateinit var ff1ad:frf1adapter
    lateinit var fmosad:frmostadapter
    lateinit var ff1: RecyclerView
    lateinit var fmo: RecyclerView
    lateinit var fmitem : ArrayList<frmost>
    companion object {
        lateinit var ff1item: ArrayList<frf1helper>
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_fruitcat)
        backbtn=findViewById(R.id.backfr)
        searchEditText=findViewById(R.id.searchEditTextfr)
        searchIcon=findViewById(R.id.searchiconfr)
        ff1=findViewById(R.id.fruitfeaturedrecycler)
        fmo=findViewById(R.id.mofruitrecy)
        backbtn()
        searchedi()
        searchedte()
        frsetupFeatured2RecyclerView()
        frsetupmostorderedRecyclerView()



    }
    private fun backbtn(){
        //backbtn will go back to profile page
        backbtn.setOnClickListener(){
            val intent = Intent(this, Profile1::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun searchedi(){
        //this will trigger searchbar by clicking the icon image and redirect to the next page where we can search
        searchIcon.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun searchedte(){
        //this will trigger searchbar by clicking the textbox and redirect to the next page where we can search
        searchEditText.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun frsetupFeatured2RecyclerView() {
        ff1.setHasFixedSize(true)
        ff1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        ff1item = arrayListOf(
            frf1helper(R.drawable.fruitkiwi, "Kiwi", "High in vitamin C; aids digestion and boosts immunity."),
            frf1helper(R.drawable.app, "Apple", "Rich in fiber and antioxidants; supports heart and gut health."),
            frf1helper(R.drawable.ban, "Banana", "Packed with potassium; boosts energy and muscle function."),
            frf1helper(R.drawable.drag, "Dragon Fruit", "Low-calorie and vitamin-rich; good for skin and immunity."),
            frf1helper(R.drawable.fruitgrape, "Grapes", "Antioxidant-rich; supports heart and brain health."),
            frf1helper(R.drawable.fruitorange, "Orange", "Excellent source of vitamin C; boosts immune system."),
            frf1helper(R.drawable.fruitwatermel, "Watermelon", "Hydrating and refreshing; rich in antioxidants."),
            frf1helper(R.drawable.fruitstrawb, "Strawberry", "Rich in vitamin C and antioxidants; good for skin."),
            frf1helper(R.drawable.app, "Apple", "High in fiber; supports digestion and heart health."),
            frf1helper(R.drawable.ban, "Banana", "Energizing and potassium-rich; good for muscles."),
            frf1helper(R.drawable.drag, "Dragon Fruit", "Boosts immunity and skin health; low in calories."),
            frf1helper(R.drawable.fruitkiwi, "Kiwi", "Aids digestion and supports immune function."),
            frf1helper(R.drawable.fruitgrape, "Grapes", "Heart-healthy and anti-inflammatory."),
            frf1helper(R.drawable.fruitorange, "Orange", "Vitamin C-packed; strengthens immunity."),
        )
        ff1ad = frf1adapter(ff1item)
        ff1.adapter = ff1ad
    }
    private fun frsetupmostorderedRecyclerView() {
        fmo.setHasFixedSize(true)
        fmo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fmitem = arrayListOf(
            frmost(R.drawable.fruitgrape, "Grapes", "Good for heart and brain."),
            frmost(R.drawable.fruitstrawb, "Strawberry", "Packed with antioxidants."),
            frmost(R.drawable.fruitwatermel, "Watermelon", "Hydrates and refreshes."),
            frmost(R.drawable.fruitorange, "Orange", "High in vitamin C."),
            frmost(R.drawable.app, "Apple", "Supports heart and gut."),
            frmost(R.drawable.ban, "Banana", "Boosts energy and muscles."),
            frmost(R.drawable.drag, "Dragon Fruit", "Great for skin and hydration."),
            frmost(R.drawable.fruitkiwi, "Kiwi", "Aids digestion and immunity."),
        )

        fmosad = frmostadapter(fmitem)
        fmo.adapter = fmosad
    }
}