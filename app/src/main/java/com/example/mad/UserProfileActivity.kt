package com.example.mad

import DbHelperUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UserProfileActivity : AppCompatActivity() {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCountry: EditText
    private lateinit var etPassword: EditText
    private lateinit var userEmail: String
    private lateinit var dbHelper: DbHelperUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        dbHelper = DbHelperUser(this)

        userEmail = intent.getStringExtra("userEmail").toString()

        etFname = findViewById(R.id.profile_fName)
        etLname = findViewById(R.id.profile_lName)
        etEmail = findViewById(R.id.profile_email)
        etCountry = findViewById(R.id.profile_country)
        etPassword = findViewById(R.id.profile_password)

        addValues()

        val logoutButton = findViewById<Button>(R.id.btn_user_logout)
        logoutButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        val updateDetailsButton = findViewById<Button>(R.id.btn_user_update_details)
        updateDetailsButton.setOnClickListener {
            updateDetails()
        }

    }

    private fun updateDetails() {
        val fName = etFname.text.toString()
        val lName = etLname.text.toString()
        val email = etEmail.text.toString()
        val country = etCountry.text.toString()
        val password = etPassword.text.toString()

        if(fName == "" || lName == "" || country == "" || email == "" || password == ""){
            Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show()
        }else{
//
            val success = dbHelper.updateUser(userEmail, firstName = fName, lastName = lName, country = country, password = password)
            if (success) {
                Toast.makeText(this, "User Updated Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "User Update Failed", Toast.LENGTH_LONG).show()
            }
            Toast.makeText(this, "Details Updated", Toast.LENGTH_LONG).show()
        }
    }

    private fun addValues(){
        val userData = dbHelper.getUser(userEmail)
        if (userData != null) {
            etFname.text = Editable.Factory.getInstance().newEditable(userData.firstName)
            etLname.text = Editable.Factory.getInstance().newEditable(userData.lastName)
            etEmail.text = Editable.Factory.getInstance().newEditable(userData.email)
            etCountry.text = Editable.Factory.getInstance().newEditable(userData.country)
            etPassword.text = Editable.Factory.getInstance().newEditable(userData.password)

        } else {
            // User not found
        }
    }
}