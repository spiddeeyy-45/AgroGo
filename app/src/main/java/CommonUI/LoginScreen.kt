package CommonUI

import Consumer.UserDashBoard
import Farmer.FarmerDashBoard
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agrogo.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class LoginScreen : AppCompatActivity() {
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
        setContentView(R.layout.login_screen)
        newuser1 = findViewById(R.id.newacc)
        logotext = findViewById(R.id.logotext1)
        slogantext = findViewById(R.id.slogan_name)
        logo1 = findViewById(R.id.logo_Image)
        pass1 = findViewById(R.id.password)
        user1 = findViewById(R.id.user_name)
        go1 = findViewById(R.id.go)
        transitionanim()
        go1.setOnClickListener() {
            //When the user clicks on the go1 button, call the loginuser function, passing the clicked view as a parameter
            loginuser(it)
        }
    }

    fun transitionanim() {
        // this transition pair which will transit from one window to another by give transition name
        newuser1.setOnClickListener() {
            val intent = Intent(
                this,
                SignUpScreen::class.java
            ) // this used jump from one page to another by giving name of page in intent
            val pairs = arrayOfNulls<Pair<View, String>>(7)
            pairs[0] = Pair(logo1, "logoo")
            pairs[1] = Pair(logotext, "logot")
            pairs[2] = Pair(slogantext, "logot")
            pairs[3] = Pair(pass1, "pass_tran")
            pairs[4] = Pair(user1, "user_tran")
            pairs[5] = Pair(go1, "go_tran")
            pairs[6] = Pair(newuser1, "newu_tran")
            val options =
                ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
            startActivity(intent, options.toBundle())
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
        }
    }

    fun isuser1() {
        val userEnter = user1.editText?.text.toString().trim()
        val userpass = pass1.editText?.text.toString().trim()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEnter, userpass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user1.error = null
                    user1.isErrorEnabled = false
                    pass1.error = null
                    pass1.isErrorEnabled = false

                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    val db = FirebaseFirestore.getInstance()

                    if (uid != null) {
                        // Check in "farmers" collection
                        db.collection("farmers").document(uid).get()
                            .addOnSuccessListener { farmerSnapshot ->
                                if (farmerSnapshot.exists()) {
                                    val firstnameFromDB = farmerSnapshot.getString("firstname")
                                    val phonenoFromDB = farmerSnapshot.getString("phoneno")
                                    val emailFromDB = farmerSnapshot.getString("email")
                                    val usernameFromDB = farmerSnapshot.getString("username")
                                    val passwordFromDB = farmerSnapshot.getString("password")
                                    val userroleFromDB = farmerSnapshot.getString("userrole")

                                    if (userroleFromDB == "Farmer") {
                                        val intent2 = Intent(this@LoginScreen, FarmerDashBoard::class.java)
                                        intent2.putExtra("name", firstnameFromDB)
                                        intent2.putExtra("phoneno", phonenoFromDB)
                                        intent2.putExtra("email", emailFromDB)
                                        intent2.putExtra("username", usernameFromDB)
                                        intent2.putExtra("password", passwordFromDB)
                                        intent2.putExtra("userrole", userroleFromDB)
                                        startActivity(intent2)
                                    }
                                } else {
                                    // Not in farmers? Check in consumers
                                    db.collection("consumers").document(uid).get()
                                        .addOnSuccessListener { consumerSnapshot ->
                                            if (consumerSnapshot.exists()) {
                                                val firstnameFromDB = consumerSnapshot.getString("firstname")
                                                val phonenoFromDB = consumerSnapshot.getString("phoneno")
                                                val emailFromDB = consumerSnapshot.getString("email")
                                                val usernameFromDB = consumerSnapshot.getString("username")
                                                val passwordFromDB = consumerSnapshot.getString("password")
                                                val userroleFromDB = consumerSnapshot.getString("userrole")

                                                if (userroleFromDB == "Consumer") {
                                                    val intent = Intent(this@LoginScreen, UserDashBoard::class.java)
                                                    intent.putExtra("name", firstnameFromDB)
                                                    intent.putExtra("phoneno", phonenoFromDB)
                                                    intent.putExtra("email", emailFromDB)
                                                    intent.putExtra("username", usernameFromDB)
                                                    intent.putExtra("password", passwordFromDB)
                                                    intent.putExtra("userrole", userroleFromDB)
                                                    startActivity(intent)
                                                }
                                            } else {
                                                Toast.makeText(this@LoginScreen, "No such User Found", Toast.LENGTH_SHORT).show()
                                                user1.error = "No such User Found"
                                                user1.requestFocus()
                                            }
                                        }
                                }
                            }

                    }
                } else {
                    pass1.error = "Wrong Email or Password"
                    pass1.requestFocus()
                }
            }
    }

}





