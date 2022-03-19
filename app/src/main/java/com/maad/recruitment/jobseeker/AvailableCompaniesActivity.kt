package com.maad.recruitment.jobseeker

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.R
import com.maad.recruitment.company.CompanyModel
import com.maad.recruitment.company.department.DepartmentModel
import com.maad.recruitment.company.employee.EmployeeModel
import com.maad.recruitment.company.job.JobModel
import com.maad.recruitment.databinding.ActivityAvailableCompaniesBinding
import android.util.Pair

class AvailableCompaniesActivity : AppCompatActivity(),
    AvailableCompaniesAdapter.OnItemClickListener {

    private lateinit var binding: ActivityAvailableCompaniesBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var companies: ArrayList<CompanyModel>
    private lateinit var departments: ArrayList<DepartmentModel>
    private lateinit var employees: ArrayList<EmployeeModel>
    private lateinit var jobs: ArrayList<JobModel>
    private var filteredDepartments = arrayListOf<DepartmentModel>()
    private var filteredEmployees = arrayListOf<EmployeeModel>()
    private var filteredJobs = arrayListOf<JobModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Companies"
        db = Firebase.firestore

        getCompanies()
    }

    override fun onItemClick(position: Int, image: ImageView, name: TextView) {
        //filter all jobs/departments/employees using company Id
        val companyId = companies[position].id
        for (employee in employees)
            if (employee.companyId == companyId)
                filteredEmployees.add(employee)

        for (department in departments)
            if (department.companyId == companyId)
                filteredDepartments.add(department)

        for (job in jobs)
            if (job.companyId == companyId)
                filteredJobs.add(job)

        val i = Intent(this, CompanyDetailsActivity::class.java)
        i.putExtra("jobs", filteredJobs)
        i.putExtra("departments", filteredDepartments)
        i.putExtra("employees", filteredEmployees)
        i.putExtra("company", companies[position])
        i.putExtra("imageTransition", ViewCompat.getTransitionName(image))
        i.putExtra("nameTransition", ViewCompat.getTransitionName(name))

        val imageTransition = Pair.create<View, String>(image, ViewCompat.getTransitionName(image))
        val nameTransition = Pair.create<View, String>(name, ViewCompat.getTransitionName(name))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, imageTransition, nameTransition)

        startActivity(i, options.toBundle())
    }

    private fun getCompanies() {
        db
            .collection("companies")
            .get()
            .addOnSuccessListener {
                companies = it.toObjects(CompanyModel::class.java) as ArrayList<CompanyModel>
                getDepartments()
            }
    }

    private fun getDepartments() {
        db
            .collection("departments")
            .get()
            .addOnSuccessListener {
                departments =
                    it.toObjects(DepartmentModel::class.java) as ArrayList<DepartmentModel>
                getEmployees()
            }
    }

    private fun getEmployees() {
        db
            .collection("employees")
            .get()
            .addOnSuccessListener {
                employees = it.toObjects(EmployeeModel::class.java) as ArrayList<EmployeeModel>
                getJobs()
            }
    }

    private fun getJobs() {
        db
            .collection("jobs")
            .get()
            .addOnSuccessListener {
                jobs = it.toObjects(JobModel::class.java) as ArrayList<JobModel>
                val adapter = AvailableCompaniesAdapter(this, companies, this)
                binding.rv.adapter = adapter
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_profile)
            startActivity(Intent(this, SeekerProfileActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

}