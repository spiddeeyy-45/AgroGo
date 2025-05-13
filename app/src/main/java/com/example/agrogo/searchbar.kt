package com.example.agrogo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrogo.Profile1.Companion.featuredItems
class searchbar : AppCompatActivity() {
    lateinit var sear: EditText
    lateinit var seaci: ImageView
    lateinit var back: ImageView
    lateinit var recycls:RecyclerView
    lateinit var searchAdapter: searadp  // ✅ Added this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_searchbar)
        recycls=findViewById(R.id.searchre)
        back=findViewById(R.id.back1)
        sear = findViewById(R.id.searchEditText1)
        seaci = findViewById(R.id.searchicon1)
        sear.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(sear, InputMethodManager.SHOW_IMPLICIT)
        backbtnfun()
        searchrecyclerview()
        searchfunction()
    }
    private fun backbtnfun(){
        back.setOnClickListener(){
            val intent = Intent(this, Profile1::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun searchfunction(){
        seaci.setOnClickListener {
            performSearchh(sear.text.toString())
        }
        sear.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                performSearchh(sear.text.toString())
                true
            } else
            {
                false
            }
        }
    }
    private fun searchrecyclerview(){
        recycls.setHasFixedSize(true)
        recycls.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        searchAdapter = searadp(featuredItems)
        recycls.adapter = searchAdapter
    }
    private fun performSearchh(query: String) {
        if (query.isNotBlank()) {
            searchAdapter.filterList(query, this@searchbar) // ✅ pass context here
        }
        else {
            Toast.makeText(this, "Please enter search text", Toast.LENGTH_SHORT).show()
        }
    }
}
