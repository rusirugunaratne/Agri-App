package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val email = intent.getStringExtra("userEmail")

        button.setOnClickListener{
            startActivity(Intent(this,AddCrop::class.java))
        }

        button2.setOnClickListener{
            startActivity(Intent(this,cropreadadmin::class.java))
        }

        btn_goto_profile.setOnClickListener{
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra("userEmail", email)
            startActivity(intent)
        }
    }
}