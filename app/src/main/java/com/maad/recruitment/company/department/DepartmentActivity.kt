package com.maad.recruitment.company.department

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
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
import com.maad.recruitment.R
import com.maad.recruitment.databinding.ActivityDepartmentBinding

class DepartmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Add Department"
        db = Firebase.firestore
        createNotificationChannel()

        binding.addDepartmentBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val description = binding.descriptionEt.text.toString()
            val vision = binding.visionEt.text.toString()
            val mission = binding.missionEt.text.toString()
            val employeesNumberText = binding.employeesNumberEt.text.toString()
            val number = employeesNumberText.toIntOrNull()
            if (name.isEmpty() || description.isEmpty() || vision.isEmpty() || mission.isEmpty()
                || employeesNumberText.isEmpty()
            )
                Toast.makeText(this, R.string.missing_fields, Toast.LENGTH_SHORT).show()
            else {
                binding.progress.visibility = View.VISIBLE
                binding.addDepartmentBtn.visibility = View.INVISIBLE
                val id = intent.getStringExtra("companyId")!!
                val department = DepartmentModel(name, description, vision, mission, number!!, id)
                addDepartment(department)
            }
        }

    }

    private fun addDepartment(department: DepartmentModel) {
        db
            .collection("departments")
            .add(department)
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                binding.addDepartmentBtn.visibility = View.VISIBLE
                val builder = NotificationCompat.Builder(this, "Department")
                    .setSmallIcon(R.drawable.ic_job)
                    .setContentTitle("Department Added")
                    .setContentText("Department Added Successfully")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                with(NotificationManagerCompat.from(this)) {
                    notify(1, builder.build())
                }
            }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Department"
            val descriptionText = "Department Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Department", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}