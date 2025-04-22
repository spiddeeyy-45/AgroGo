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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class newuser : AppCompatActivity() {
    lateinit var  fullnameu: TextInputLayout
    lateinit var  username2:TextInputLayout
    lateinit var  phoneno:TextInputLayout
    lateinit var  email1:TextInputLayout
    lateinit var  pass2:TextInputLayout
    lateinit var  go2:Button
    lateinit var  alreadyacc:Button
    lateinit var logo2:ImageView
    lateinit var logot2:TextView
    lateinit var slogant2:TextView
    lateinit var rootNode:Firebase
    lateinit var refrence:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_newuser)
        logo2=findViewById(R.id.logo_imageu)
        logot2=findViewById(R.id.logo_textu)
        slogant2=findViewById(R.id.slogan_textu)
        fullnameu = findViewById(R.id.username)
        username2 = findViewById(R.id.username1)
        email1 = findViewById(R.id.email)
        phoneno = findViewById(R.id.phoneno)
        pass2 =  findViewById(R.id.password1)
        go2= findViewById(R.id.creatego)
        alreadyacc=findViewById(R.id.alreadyacc)
        go2.setOnClickListener {
            val rootNode = FirebaseDatabase.getInstance()
            val reference = rootNode.reference
            val firstnamed = fullnameu.editText?.text.toString()
            val usernamed = username2.editText?.text.toString()
            val emaild = email1.editText?.text.toString()
            val phonenod = phoneno.editText?.text.toString()
            val passwordd = pass2.editText?.text.toString()

            // Call the validation functions
            val isFullNameValid = validateFullname()
            val isUsernameValid = validateUsername()
            val isEmailValid = validateEmail()
            val isPhoneNoValid = validatephoneno()
            val isPasswordValid = validatepass()
            // Check if all validations are true before proceeding
            when {
                !isFullNameValid -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT)
                        .show()
                }

                !isUsernameValid -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }

                !isEmailValid -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }

                !isPhoneNoValid -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT)
                        .show()
                }

                !isPasswordValid -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    // If all validations pass, create the user object and store it in Firebase
                    val user = UserHelper(firstnamed, usernamed, emaild, phonenod, passwordd)

                    // Check if any field is empty before storing in Firebase
                    if (firstnamed.isEmpty() || usernamed.isEmpty() || emaild.isEmpty() || phonenod.isEmpty() || passwordd.isEmpty()) {
                        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        // Store the user data in Firebase
                        val nameRef = reference.child("user")
                        nameRef.push().setValue(user)

                        // Start the transition to the dashboard
                        val intent = Intent(this, DashBoard::class.java)
                        val pairs = arrayOfNulls<Pair<View, String>>(7)
                        pairs[0] = Pair(logo2, "logoo")
                        pairs[1] = Pair(logot2, "logot")
                        pairs[2] = Pair(slogant2, "logot")
                        pairs[3] = Pair(pass2, "pass_tran")
                        pairs[4] = Pair(username2, "user_tran")
                        pairs[5] = Pair(go2, "go_tran")
                        pairs[6] = Pair(alreadyacc, "newu_tran")
                        val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
                        startActivity(intent, options.toBundle())

                        Toast.makeText(this, "Please Login with the ID You created", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        alreadyacc.setOnClickListener(){
            val intent = Intent(this, DashBoard::class.java)
            val pairs = arrayOfNulls<Pair<View, String>>(7)
            pairs[0] = Pair(logo2, "logoo")
            pairs[1] = Pair(logot2, "logot")
            pairs[2] = Pair(slogant2, "logot")
            pairs[3] = Pair(pass2, "pass_tran")
            pairs[4] = Pair(username2, "user_tran")
            pairs[5] = Pair(go2, "go_tran")
            pairs[6] = Pair(alreadyacc, "newu_tran")
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs.requireNoNulls())
            startActivity(intent,options.toBundle())
        }
    }
    fun validateUsername(): Boolean {
        val user = username2.editText?.text.toString()
        val nowhitespace = "\\A\\w{4,15}\\z".toRegex()

        return when {
            user.isEmpty() -> {
                username2.error = "Field cannot be empty"
                false
            }
            !user.matches(nowhitespace) -> {
                username2.error = "No white spaces & 15 characters only"
                false
            }
            else -> {
                username2.error = null
                true
            }
        }
        }
    fun validatepass(): Boolean {
        val pss = pass2.editText?.text.toString()
        val passval = ("^" +
                "(?=.*[0-9])" +         // at least one digit
                "(?=.*[a-z])" +         // at least one lowercase letter
                "(?=.*[A-Z])" +         // at least one uppercase letter
                "(?=.*[@#\$%^&!*+=])" +   // at least one special character
                "(?=\\S+$)" +           // no white spaces
                ".{8,}" +               // at least 8 characters
                "$").toRegex()
        return when {
            pss.isEmpty() -> {
                pass2.error = "Field cannot be empty"
                false
            }

            !pss.matches(passval) -> {
                pass2.error = "Password must include uppercase, lowercase, digit, special char, and no spaces"
                false
            }

            else -> {
                pass2.error = null
                //pass1.isErrorEnabled = false
                true
            }
        }
    }
    fun validateEmail(): Boolean {
        val mail = email1.editText?.text.toString()
        val emailRegex = "^[A-Za-z0-9](?:[A-Za-z0-9._%+-]{0,63})@[A-Za-z0-9.-]+\\.com$".toRegex()

        return when {
            mail.isEmpty() -> {
                email1.error = "Field cannot be empty"
                false
            }

            !mail.matches(emailRegex) -> {
                email1.error = "Enter a valid email address ending with .com and without special characters at the beginning"
                false
            }

            else -> {
                email1.error = null
                true
            }
        }
    }
    fun validateFullname(): Boolean {
        val name = fullnameu.editText?.text.toString()
        val nameRegex = "^[A-Za-z]{2,}(?: [A-Za-z]{2,}){0,2}$".toRegex()


        return when {
            name.isEmpty() -> {
                fullnameu.error = "Field cannot be empty"
                false
            }
            !name.matches(nameRegex) || name.length < 4 || name.length > 20 -> {
                fullnameu.error = "Enter 1–3 words, 4–20 characters, letters only"
                false
            }
            else -> {
                fullnameu.error = null
                true
            }
        }
    }
    fun validatephoneno(): Boolean {
        val phoneno1 = phoneno.editText?.text.toString()
        val phonenoRegex = "^(\\+\\d{1,3})?[0-9]{10}$".toRegex()
        return when {
            phoneno1.isEmpty() -> {
                phoneno.error = "Field cannot be empty"
                false
            }

            !phoneno1.matches(phonenoRegex) -> {
                phoneno.error = "Enter a valid 10-digit phone number"
                false
            }

            else -> {
                phoneno.error = null
                true
            }
        }
    }



}