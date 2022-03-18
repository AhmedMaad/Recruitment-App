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

        //read "companies" collection from Firebase along with employees/department/jobs
        //then show them in our recycler view
        //When clicking on an item, filter using the clicked "Item" company ID

    }
}