package com.example.agrogo

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class DashBoard : AppCompatActivity() {
    lateinit var newuser1: Button
    lateinit var logo1: ImageView
    lateinit var logotext: TextView
    lateinit var slogantext: TextView
    lateinit var pass1: TextInputLayout
    lateinit var user1: TextInputLayout
    lateinit var go1: Button
    lateinit var rootNode1: Firebase
    lateinit var refrence1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
       //this will remove top navbar like charging percent ,network this will do the full screen removing navbar
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
       // this are hook
        setContentView(R.layout.activity_dash_board)
        newuser1 = findViewById(R.id.newacc)
        logotext = findViewById(R.id.logotext1)
        slogantext = findViewById(R.id.slogan_name)
        logo1 = findViewById(R.id.logo_Image)
        pass1 = findViewById(R.id.password)
        user1 = findViewById(R.id.user_name)
        go1 = findViewById(R.id.go)
        // this transition pair which will transit from one window to another by give transition name
        newuser1.setOnClickListener() {
            val intent = Intent(this, newuser::class.java) // this used jump from one page to another by giving name of page in intent
            val pairs = arrayOfNulls<Pair<View, String>>(7)
            pairs[0] = Pair(logo1, "logoo")
            pairs[1] = Pair(logotext, "logot")
            pairs[2] = Pair(slogantext, "logot")
            pairs[3] = Pair(pass1, "pass_tran")
            pairs[4] = Pair(user1, "user_tran")
            pairs[5] = Pair(go1, "go_tran")
            pairs[6] = Pair(newuser1, "newu_tran")
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
            startActivity(intent, options.toBundle())
        }
        go1.setOnClickListener(){
            loginuser(it)
        }
        }
    fun validateUsername(): Boolean {
        val user = user1.editText?.text.toString()
       // val nowhitespace = "\\A\\w{4,20}\\z".toRegex()

        return when {
            user.isEmpty() -> {
                user1.error = "Field cannot be empty"
                false
            }
            //!user.matches(nowhitespace) -> {
              //  user1.error = "No white spaces & 4-20 characters only"
              //  false
            //}

            else -> {
                user1.error = null
                user1.isErrorEnabled = false
                true
            }
        }
    }

    fun validatepass(): Boolean {
        val pss = pass1.editText?.text.toString()

        return when {
            pss.isEmpty() -> {
                pass1.error = "Field cannot be empty"
                false
            }

            else -> {
                pass1.error = null
                pass1.isErrorEnabled = false
                true
            }
        }

    }

    fun loginuser(view: View) {
        if (!validateUsername() || !validatepass()) {
            return

        } else {
            isuser1()
        }}

    fun isuser1()   {
        val userEnter = user1.editText?.text.toString().trim()
        val userpass = pass1.editText?.text.toString().trim()

        // Reference to Firebase
        val rootNode = FirebaseDatabase.getInstance().reference.child("user")
        val checkUser = rootNode.orderByChild("username").equalTo(userEnter)

        // Add listener for Firebase data changes
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Reset errors
                    user1.error = null
                    user1.isErrorEnabled = false
                    // Iterate through the user data
                    for (userSnapshot in snapshot.children) {
                        pass1.error = null
                        pass1.isErrorEnabled = false
                        val passwordFromDB = userSnapshot.child("password").getValue(String::class.java)
                        // Check if password matches
                        if (passwordFromDB == userpass) {
                            // Retrieve user details
                            val firstnameFromDB =
                                userSnapshot.child("firstname").getValue(String::class.java)
                            val phonenoFromDB =
                                userSnapshot.child("phoneno").getValue(String::class.java)
                            val emailFromDB =
                                userSnapshot.child("email").getValue(String::class.java)
                            val usernameFromDB =
                                userSnapshot.child("username").getValue(String::class.java)
                            // Create intent and pass data to Profile1 activity
                            val intent2 = Intent(this@DashBoard, Profile1::class.java)
                            intent2.putExtra("name", firstnameFromDB)
                            intent2.putExtra("phoneno", phonenoFromDB)
                            intent2.putExtra("email", emailFromDB)
                            intent2.putExtra("username", usernameFromDB)
                            intent2.putExtra("password", passwordFromDB)
                            startActivity(intent2)
                        }
                        else
                        {
                            // Show error if password is incorrect
                            pass1.error = "Wrong Password"
                            pass1.requestFocus()
                        }
                    }
                }
                else
                {
                    // Show error if user does not exist
                    user1.error = "No such User Found"
                    user1.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any database error
                Log.e("Firebase", "Database error: ${error.message}")
            }
        })
    }

}




