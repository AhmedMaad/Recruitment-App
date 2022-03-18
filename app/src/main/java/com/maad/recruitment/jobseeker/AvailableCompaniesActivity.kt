package com.maad.recruitment.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.R
import com.maad.recruitment.company.CompanyModel
import com.maad.recruitment.company.department.DepartmentModel
import com.maad.recruitment.company.employee.EmployeeModel
import com.maad.recruitment.company.job.JobModel
import com.maad.recruitment.databinding.ActivityAvailableCompaniesBinding

class AvailableCompaniesActivity : AppCompatActivity(),
    AvailableCompaniesAdapter.OnItemClickListener {

    private lateinit var binding: ActivityAvailableCompaniesBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var companies: ArrayList<CompanyModel>
    private lateinit var departments: ArrayList<DepartmentModel>
    private lateinit var employees: ArrayList<EmployeeModel>
    private lateinit var jobs: ArrayList<JobModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Companies"
        db = Firebase.firestore

        //read "companies" collection from Firebase along with employees/department/jobs
        //then show them in our recycler view
        //When clicking on an item, filter using the clicked "Item" company ID
        db
            .collection("companies")
            .get()
            .addOnSuccessListener {
                companies = it.toObjects(CompanyModel::class.java) as ArrayList<CompanyModel>
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

    override fun onItemClick(position: Int, view: View) {
        Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show();
    }

}