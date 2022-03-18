package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivityAvailableCompaniesBinding

class AvailableCompaniesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableCompaniesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Companies"

        //make recycler view that shows companies using joneer list item
        //with logo, name, about with (see more..)

    }
}