package com.maad.recruitment.company

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.recruitment.ExitDialog
import com.maad.recruitment.company.department.DepartmentActivity
import com.maad.recruitment.company.employee.EmployeeActivity
import com.maad.recruitment.company.job.JobActivity
import com.maad.recruitment.databinding.ActivityCompanyProfileBinding
import java.util.*


class CompanyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyProfileBinding
    private var imageUri: Uri? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var company: CompanyModel
    private lateinit var id: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        val prefs = getSharedPreferences("authentication", MODE_PRIVATE)
        id = prefs.getString("id", null)!!
        email = prefs.getString("email", null)!!

        binding.tvEmail.text = email

        db
            .collection("companies")
            .document(id)
            .get()
            .addOnSuccessListener {
                company = it.toObject(CompanyModel::class.java) ?: CompanyModel()
                if (company.name.isNotEmpty()) {
                    title = "${company.name} Profile"
                    binding.nameEt.setText(company.name)
                    binding.detailsEt.setText(company.details)
                    binding.phoneEt.setText(company.number)
                    binding.locationEt.setText(company.location)
                    binding.tvEmail.text = company.email
                    Glide
                        .with(this)
                        .load(company.image)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .into(binding.profileIv)
                }

            }

        binding.profilePictureCv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
            //Log.d("trace", "Choosing a picture")
        }

        binding.addJobBtn.setOnClickListener {
            val i = Intent(this, JobActivity::class.java)
            i.putExtra("companyId", id)
            startActivity(i)
        }

        binding.addDeptBtn.setOnClickListener {
            val i = Intent(this, DepartmentActivity::class.java)
            i.putExtra("companyId", id)
            startActivity(i)
        }

        binding.addEmployeeBtn.setOnClickListener {
            val i = Intent(this, EmployeeActivity::class.java)
            i.putExtra("companyId", id)
            startActivity(i)
        }

        binding.doneIv.setOnClickListener {
            //Log.d("trace", "Clicked Done")
            binding.progress.visibility = View.VISIBLE
            binding.doneIv.visibility = View.INVISIBLE
            if (imageUri != null)
                uploadImage()
            else {
                //Log.d("trace", "No Image Chosen")
                uploadProfile(null)
            } //This means that the user already has a pp

        }

    }

    private fun uploadImage() {
        //Log.d("trace", "Uploading Image")
        //Accessing Cloud Storage bucket by creating an instance of FirebaseStorage
        val storage = FirebaseStorage.getInstance()
        //Create a reference to upload, download, or delete a file
        val now: Calendar = Calendar.getInstance()
        val y: Int = now.get(Calendar.YEAR)
        val m: Int = now.get(Calendar.MONTH) + 1 // Note: zero based!

        val d: Int = now.get(Calendar.DAY_OF_MONTH)
        val h: Int = now.get(Calendar.HOUR_OF_DAY)
        val min: Int = now.get(Calendar.MINUTE)
        val s: Int = now.get(Calendar.SECOND)
        val imageName = "image: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(imageName)
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    //Log.d("trace", "Getting Download link")
                    uploadProfile(it)
                }
            }
    }

    private fun uploadProfile(imageUri: Uri?) {
        //Log.d("trace", "Uploading Profile")
        val details = binding.detailsEt.text.toString()
        val phoneNumber = binding.phoneEt.text.toString()
        val location = binding.locationEt.text.toString()
        val name = binding.nameEt.text.toString()
        val imageLink = imageUri?.toString() ?: company.image

        company = CompanyModel(imageLink, name, email, details, phoneNumber, location, id)

        db
            .collection("companies")
            .document(id)
            .set(company)
            .addOnSuccessListener {
                //Log.d("trace", "Profile Uploaded")
                binding.progress.visibility = View.INVISIBLE
                binding.doneIv.visibility = View.VISIBLE
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            //Log.d("trace", "Chosen Image: $imageUri")
            binding.profileIv.setImageURI(imageUri)
        }
    }

    override fun onBackPressed() {
        val exit = ExitDialog()
        exit.isCancelable = false
        exit.show(supportFragmentManager, null)
    }

}