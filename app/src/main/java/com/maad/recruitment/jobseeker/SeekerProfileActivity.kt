package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivitySeekerProfileBinding

class SeekerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeekerProfileBinding
    private lateinit var id: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeekerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("authentication", MODE_PRIVATE)
        id = prefs.getString("id", null)!!
        email = prefs.getString("email", null)!!

        /*read seeker name (first and last) from firebase using id first time EVERYTIME*/
        /*Read seeler email from shared pref EVERYTIME*/



    }
}