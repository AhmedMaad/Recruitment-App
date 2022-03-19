package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.maad.recruitment.company.CompanyModel
import com.maad.recruitment.company.department.DepartmentModel
import com.maad.recruitment.company.employee.EmployeeModel
import com.maad.recruitment.company.job.JobModel
import com.maad.recruitment.databinding.ActivityCompanyDetailsBinding

class CompanyDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val company = intent.getParcelableExtra<CompanyModel>("company")!!
        val jobs = intent.getParcelableArrayListExtra<JobModel>("jobs")!!
        val departments = intent.getParcelableArrayListExtra<DepartmentModel>("departments")!!
        val employees = intent.getParcelableArrayListExtra<EmployeeModel>("employees")!!

        binding.companyProfileIv.transitionName = intent.getStringExtra("imageTransition")
        binding.companyNameTv.transitionName = intent.getStringExtra("nameTransition")

        Glide.with(this).load(company.image).into(binding.companyProfileIv)
        binding.companyNameTv.text = company.name
        binding.companyEmailTv.text  = company.email
        binding.companyPhoneTv.text = company.number
        binding.companyLocationTv.text = company.location
        binding.companyAboutTv.text = company.details


    }
}