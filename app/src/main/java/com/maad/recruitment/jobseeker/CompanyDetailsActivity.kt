package com.maad.recruitment.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        binding.companyProfileIv.transitionName = intent.getStringExtra("imageTransition")
        binding.companyNameTv.transitionName = intent.getStringExtra("nameTransition")

        val company = intent.getParcelableExtra<CompanyModel>("company")!!
        Glide.with(this).load(company.image).into(binding.companyProfileIv)
        binding.companyNameTv.text = company.name
        binding.companyEmailTv.text = company.email
        binding.companyPhoneTv.text = company.number
        binding.companyLocationTv.text = company.location
        binding.companyAboutTv.text = company.details

        val jobs = intent.getParcelableArrayListExtra<JobModel>("jobs")!!
        if (jobs.size == 0)
            binding.jobsKeyTv.text = "No Jobs Available"
        else {
            val jobsAdapter = JobsAdapter(this, jobs)
            binding.jobsRv.adapter = jobsAdapter
        }

        val departments = intent.getParcelableArrayListExtra<DepartmentModel>("departments")!!
        if (departments.size == 0)
            binding.departmentsKeyTv.text = "No Added Departments"
        else {
            val departmentsAdapter = DepartmentsAdapter(this, departments)
            binding.departmentsRv.adapter = departmentsAdapter
        }

        val employees = intent.getParcelableArrayListExtra<EmployeeModel>("employees")!!
        if (employees.size == 0)
            binding.employeesKeyTv.text = "No Employees yet"
        else {
            val employeesAdapter = EmployeesAdapter(this, employees)
            binding.employeesRv.adapter = employeesAdapter
        }


    }

}