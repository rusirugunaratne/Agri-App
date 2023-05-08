package com.example.mad

import DbHelperUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var fName: EditText
    private lateinit var lName: EditText
    private lateinit var coutry: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var passwordConfirm: EditText
    private lateinit var dbHelper: DbHelperUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelperUser(this)

        fName = findViewById(R.id.register_fName)
        lName = findViewById(R.id.register_lName)
        coutry = findViewById(R.id.register_country)
        email = findViewById(R.id.register_email)
        password = findViewById(R.id.register_password)
        passwordConfirm = findViewById(R.id.register_confirmPassword)

        val signUpButton = findViewById<Button>(R.id.btn_signup)
        signUpButton.setOnClickListener{
            if(fName.text.toString() == "" || lName.text.toString() == "" || coutry.text.toString() == "" || email.text.toString() == "" || password.text.toString() == "" || passwordConfirm.text.toString() == ""){
                Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show()
            }else{
                if(password.text.toString() != passwordConfirm.text.toString()){
                    password.error = "Passwords Not Matching"
                    passwordConfirm.error = "Passwords Not Matching"
                    Toast.makeText(this, "Passwords not matching", Toast.LENGTH_LONG).show()
                }else{
                    saveUser()
                }
            }
        }
    }

    private fun saveUser(){
        val uFName = fName.text.toString()
        val uLName = lName.text.toString()
        val uCountry = coutry.text.toString()
        val uEmail = email.text.toString()
        val uPassword = password.text.toString()

        val success = dbHelper.addUser(uFName, uLName, uCountry, uEmail, uPassword)
        val user = dbHelper.getUser(uEmail)
        if (user != null) {
            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }

        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("userEmail", uEmail)
        startActivity(intent)
    }
}