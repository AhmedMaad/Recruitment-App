package com.maad.recruitment.company.employee

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.recruitment.R
import com.maad.recruitment.databinding.ActivityEmployeeBinding
import java.util.*

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeBinding
    private lateinit var db: FirebaseFirestore
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        createNotificationChannel()

        binding.profilePictureCv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
        }

        binding.addEmployeeBtn.setOnClickListener {
            if (imageUri != null) {
                binding.progress.visibility = View.VISIBLE
                binding.addEmployeeBtn.visibility = View.INVISIBLE
                uploadImage()
            } else
                Toast.makeText(this, "Choose an image first", Toast.LENGTH_SHORT).show()
        }


    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        val now: Calendar = Calendar.getInstance()
        val y: Int = now.get(Calendar.YEAR)
        val m: Int = now.get(Calendar.MONTH) + 1

        val d: Int = now.get(Calendar.DAY_OF_MONTH)
        val h: Int = now.get(Calendar.HOUR_OF_DAY)
        val min: Int = now.get(Calendar.MINUTE)
        val s: Int = now.get(Calendar.SECOND)
        val imageName = "image: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(imageName)
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    addEmployee(it)
                }
            }
    }

    private fun addEmployee(imageUri: Uri?) {
        val name = binding.nameEt.text.toString()
        val role = binding.roleEt.text.toString()
        val funQuote = binding.funQuoteEt.text.toString()
        val id = intent.getStringExtra("companyId")!!

        if (name.isEmpty() || role.isEmpty() || funQuote.isEmpty())
            Toast.makeText(this, R.string.missing_fields, Toast.LENGTH_SHORT).show()
        else {
            val employee = EmployeeModel(imageUri.toString(), name, role, funQuote, id)
            db
                .collection("employees")
                .add(employee)
                .addOnSuccessListener {
                    binding.progress.visibility = View.INVISIBLE
                    binding.addEmployeeBtn.visibility = View.VISIBLE
                    val builder = NotificationCompat.Builder(this, "Employee")
                        .setSmallIcon(R.drawable.ic_job)
                        .setContentTitle("Employee Added")
                        .setContentText("Employee Added Successfully")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                    with(NotificationManagerCompat.from(this)) {
                        notify(1, builder.build())
                    }
                }
        }


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Employee"
            val descriptionText = "Employee Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Employee", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            binding.profileIv.setImageURI(imageUri)
        }
    }

}