package com.maad.recruitment.recruiter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivityAvailableEmployeesBinding

class AvailableEmployeesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableEmployeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableEmployeesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}