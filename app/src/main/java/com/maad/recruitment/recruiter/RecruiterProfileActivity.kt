package com.maad.recruitment.recruiter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivityRecruiterProfileBinding

class RecruiterProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecruiterProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecruiterProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}