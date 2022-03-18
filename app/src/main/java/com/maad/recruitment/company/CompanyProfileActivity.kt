package com.maad.recruitment.company

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.recruitment.databinding.ActivityCompanyProfileBinding
import java.util.*


class CompanyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyProfileBinding
    private var imageUri : Uri? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Profile"
        db = Firebase.firestore

        //Choosing to add "departments - jobs - employees" will be pushed
        //to firebase immediately using company id

        //read company data from firebase

        //show progress when adding and hide "done" icon

        //TODO: Return the email from firebase to be shown in the text view
        

        binding.profilePictureCv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
        }

        binding.addJobBtn.setOnClickListener {
            startActivity(Intent(this, JobActivity::class.java))
        }

        binding.addDeptBtn.setOnClickListener {
            startActivity(Intent(this, DepartmentActivity::class.java))
        }

        binding.addEmployeeBtn.setOnClickListener {
            startActivity(Intent(this, EmployeeActivity::class.java))
        }

        binding.doneIv.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.doneIv.visibility = View.INVISIBLE
            if (imageUri != null)
                uploadImage()
        }

    }

    private fun uploadImage() {
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
                    uploadProfile(it)
                }
            }
    }

    private fun uploadProfile(imageUri: Uri?) {
        val details = binding.detailsEt.text.toString()
        val phoneNumber = binding.phoneEt.text.toString()
        val location = binding.locationEt.text.toString()
        val name = binding.nameEt.text.toString()
        //TODO: Upload object using "companies" collection
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            binding.profileIv.setImageURI(imageUri);
        }
    }

}