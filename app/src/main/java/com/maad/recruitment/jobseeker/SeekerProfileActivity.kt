package com.maad.recruitment.jobseeker

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.maad.recruitment.company.CompanyModel
import com.maad.recruitment.databinding.ActivitySeekerProfileBinding
import com.maad.recruitment.register.User
import java.util.*

class SeekerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeekerProfileBinding
    private lateinit var id: String
    private lateinit var email: String
    private lateinit var db: FirebaseFirestore
    private var imageUri: Uri? = null
    private var cvUri: Uri? = null
    var pdfName: String? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var seeker: SeekerModel
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeekerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        storage = Firebase.storage

        val prefs = getSharedPreferences("authentication", MODE_PRIVATE)
        id = prefs.getString("id", null)!!
        email = prefs.getString("email", null)!!

        db
            .collection("users")
            .document(id)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                binding.tvEmail.text = user?.email
                name = "${user?.fname} ${user?.lname}"
                binding.nameTv.text = name
                title = "$name Profile"
            }

        db
            .collection("seekers")
            .document(id)
            .get()
            .addOnSuccessListener {
                seeker = it.toObject(SeekerModel::class.java) ?: SeekerModel()
                if (seeker.experience.isNotEmpty()) {
                    binding.experienceEt.setText(seeker.experience)
                    binding.phoneEt.setText(seeker.number)
                    binding.trackEt.setText(seeker.track)
                    binding.uploadCvBtn.text = "Update CV"
                    Glide
                        .with(this)
                        .load(seeker.picture)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .into(binding.profileIv)
                }

            }

        binding.profilePictureCv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
        }

        binding.uploadCvBtn.setOnClickListener{
            val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
            i.type = "application/pdf"
            startActivityForResult(i, 404)
        }

        binding.doneIv.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.doneIv.visibility = View.INVISIBLE
            if (imageUri != null && cvUri != null)
                uploadImage()
            else if(seeker.picture.isNotEmpty() && seeker.cv.isNotEmpty())
                uploadProfile(null, null)
            else{
                binding.progress.visibility = View.INVISIBLE
                binding.doneIv.visibility = View.VISIBLE
                Toast.makeText(this, "Upload Picture & CV", Toast.LENGTH_SHORT).show();
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            binding.profileIv.setImageURI(imageUri)
        } else if (resultCode == RESULT_OK && requestCode == 404) {
            cvUri = data!!.data
            val uriString = cvUri.toString()
            Log.d("trace", "URI string: $uriString")

            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    // Setting the PDF to the TextView
                    myCursor =
                        applicationContext!!.contentResolver.query(cvUri!!, null, null, null, null)
                    if (myCursor != null && myCursor.moveToFirst()) {
                        pdfName =
                            myCursor.getString(myCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                        binding.uploadCvBtn.text = pdfName
                        binding.uploadCvBtn.gravity = Gravity.START
                    }
                } finally {
                    myCursor?.close()
                }
            }

        }
    }

    private fun uploadImage() {
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
                    Log.d("trace", "Image Link: $it")
                    uploadCV(it)
                }
            }
    }

    private fun uploadCV(imageUri: Uri) {
        val now: Calendar = Calendar.getInstance()
        val y: Int = now.get(Calendar.YEAR)
        val m: Int = now.get(Calendar.MONTH) + 1 // Note: zero based!

        val d: Int = now.get(Calendar.DAY_OF_MONTH)
        val h: Int = now.get(Calendar.HOUR_OF_DAY)
        val min: Int = now.get(Calendar.MINUTE)
        val s: Int = now.get(Calendar.SECOND)
        val cvName = "$pdfName: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(cvName)
        storageRef.putFile(cvUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    Log.d("trace", "CV Link: $it")
                    uploadProfile(it, imageUri)
                }
            }
    }

    private fun uploadProfile(cvUri: Uri?, imageUri: Uri?) {
        Log.d("trace", "Uploading Profile...")
        val experience = binding.experienceEt.text.toString()
        val phoneNumber = binding.phoneEt.text.toString()
        val track = binding.trackEt.text.toString()
        val imageLink = imageUri?.toString() ?: seeker.picture
        //Log.d("trace", "Image Link: $imageLink")
        val cvLink = cvUri?.toString() ?: seeker.cv
        //Log.d("trace", "CV Link: $cvLink")
        seeker = SeekerModel(imageLink, experience, phoneNumber, track, cvLink, id, name, email)

        db
            .collection("seekers")
            .document(id)
            .set(seeker)
            .addOnSuccessListener {
                //Log.d("trace", "Profile Uploaded")
                binding.progress.visibility = View.INVISIBLE
                binding.doneIv.visibility = View.VISIBLE
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }

    }

}