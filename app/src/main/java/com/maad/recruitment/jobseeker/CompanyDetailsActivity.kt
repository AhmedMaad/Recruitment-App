package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivityCompanyDetailsBinding

class CompanyDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}