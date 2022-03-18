package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivitySeekerProfileBinding

class SeekerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeekerProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeekerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}