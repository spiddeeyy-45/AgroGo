package com.example.agrogo

import ReHelper
import adapterre
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity.*
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import mostordedre
import remHelper

class Profile1 : AppCompatActivity() , OnNavigationItemSelectedListener{
    lateinit var recycl:RecyclerView
    lateinit var recyl2:RecyclerView
    lateinit var readp:adapterre
    lateinit var remdp:RecyclerView.Adapter<mostordedre.remvieholder>
    lateinit var dw : DrawerLayout
    lateinit var nab:NavigationView
    lateinit var mn:ImageView
    lateinit var vegcbtn:ImageView
    lateinit var fruitcbtn:ImageView
    lateinit var seedcbtn:ImageView
    lateinit var contentv:LinearLayout
    lateinit var searchEditText:EditText
    lateinit var searchIcon:ImageView
    companion object {
        lateinit var featuredItems: ArrayList<ReHelper>
    }
    val END_SCALE:Float=0.7f
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this is used for doing the fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_profile1)
        vegcbtn=findViewById(R.id.vegc)
        fruitcbtn=findViewById(R.id.fruitc)
        seedcbtn=findViewById(R.id.seedc)
        searchEditText=findViewById(R.id.searchEditText)
        searchIcon=findViewById(R.id.searchicon)
        recycl=findViewById(R.id.featuredrecycler)
        recyl2=findViewById(R.id.morecy)
        dw=findViewById(R.id.drwa_main)
        mn=findViewById(R.id.mennav)
        nab=findViewById(R.id.navside)
        contentv=findViewById(R.id.contentv)
        //this will trigger fruits category by clicking on the image
        fruitcbtn.setOnClickListener(){
            val intent = Intent(this, fruitcat::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger seed category by clicking on the image
        seedcbtn.setOnClickListener(){
            val intent = Intent(this, seedcat::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger vegetable category by clicking on the image
        vegcbtn.setOnClickListener(){
            val intent = Intent(this, vegetablecate::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger searchbar by clicking the icon image and redirect to the next page where we can search
        searchIcon.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
        //this will trigger searchbar by clicking the textbox and redirect to the next page where we can search
        searchEditText.setOnClickListener(){
            val intent = Intent(this, searchbar::class.java)
            startActivity(intent)
            finish()
        }
        navdrawer()
        setupFeaturedRecyclerView()
        setupmostorderedRecyclerView()
    }
    //navigation bar controling function are here
    private fun navdrawer() {
        nab.bringToFront()
        nab.setNavigationItemSelectedListener(this)
        nab.setCheckedItem(R.id.nv_home)
        mn.setOnClickListener(){
            if (dw.isDrawerOpen(GravityCompat.START)) {
                dw.closeDrawer(GravityCompat.START)
            }
            else {
                dw.openDrawer(GravityCompat.START)
            }
        }
     animatenavbar()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true

    }
    override fun onBackPressed() {

        if (dw.isDrawerOpen(GravityCompat.START)) {
            dw.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }

    }
    private fun animatenavbar() {
        dw.setScrimColor(getColor(R.color.white))
        dw.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaleOffset: Float = slideOffset * (1 - END_SCALE)
                val offsetscale :Float = 1-diffScaleOffset
                contentv.setScaleX(offsetscale)
                contentv.setScaleY(offsetscale)
                val xoffset=drawerView.width*slideOffset
                val xoffsetdiff=contentv.width*diffScaleOffset/2
                val xtranslation=xoffset-xoffsetdiff
                contentv.translationX=xtranslation
            }

            override fun onDrawerOpened(drawerView: View) {
                // You can leave empty if you don't need it
            }

            override fun onDrawerClosed(drawerView: View) {
                // You can leave empty if you don't need it
            }

            override fun onDrawerStateChanged(newState: Int) {
                // You can leave empty if you don't need it
            }
        })




    }
    //this function are for recycler view for images in the list
    private fun setupFeaturedRecyclerView() {
    recycl.setHasFixedSize(true)
    recycl.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    featuredItems = arrayListOf(
        ReHelper(R.drawable.carr, "Carrot", "Carrots boost eyesight, skin, immunity, and heart health; low-cal, high fiber"),
        ReHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
        ReHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
        ReHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
        ReHelper(R.drawable.whe, "Wheat", "Rich in fiber and nutrients, wheat supports digestion and heart health"),
        ReHelper(R.drawable.carr, "Carrot", "Carrots boost eyesight, skin, immunity, and heart health; low-cal, high fiber"),
        ReHelper(R.drawable.carr, "Carrot", "Carrots boost eyesight, skin, immunity, and heart health; low-cal, high fiber"),
        ReHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
        ReHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
        ReHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
        ReHelper(R.drawable.whe, "Wheat", "Rich in fiber and nutrients, wheat supports digestion and heart health"),
        ReHelper(R.drawable.carr, "Carrot", "Carrots boost eyesight, skin, immunity, and heart health; low-cal, high fiber"),
        ReHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
        ReHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
        ReHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
        ReHelper(R.drawable.whe, "Wheat", "Rich in fiber and nutrients, wheat supports digestion and heart health")



    )

    readp = adapterre(featuredItems)
    recycl.adapter = readp
}
    private fun setupmostorderedRecyclerView() {
        recyl2.setHasFixedSize(true)
        recyl2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val featuredItemm = listOf(
            remHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
            remHelper(R.drawable.ban, "Bananaapple", "High in potassium and energy, bananas aid muscle and heart function"),
            remHelper(R.drawable.drag, "Dragonapple", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
            remHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
            remHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
            remHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
            remHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
            remHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
            remHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
            remHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
            remHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
            remHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
            remHelper(R.drawable.carr, "Carrotapple", "Carrots boost eyesight, skin, immunity, and heart health; low-cal, high fiber"),
            remHelper(R.drawable.app, "Apple", "Packed with fiber and antioxidants, apples support heart and gut health"),
            remHelper(R.drawable.ban, "Banana", "High in potassium and energy, bananas aid muscle and heart function"),
            remHelper(R.drawable.drag, "Dragon", "Low-cal and vitamin-rich, dragon fruit boosts immunity and skin health"),
            remHelper(R.drawable.whe, "Wheatapple", "Rich in fiber and nutrients, wheat supports digestion and heart health")
        )

        remdp = mostordedre(featuredItemm)
        recyl2.adapter = remdp
    }
}




