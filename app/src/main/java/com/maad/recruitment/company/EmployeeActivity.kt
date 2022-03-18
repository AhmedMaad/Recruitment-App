package com.maad.recruitment.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.recruitment.databinding.ActivityEmployeeBinding

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //show progress bar and hide add button while adding a job

    }
}