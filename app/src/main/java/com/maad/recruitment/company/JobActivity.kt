package com.maad.recruitment.company

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.R
import com.maad.recruitment.databinding.ActivityJobBinding

class JobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Add a Job"
        db = Firebase.firestore
        createNotificationChannel()

        val currencies = arrayOf('$', '£', '€')
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.currencySpinner.adapter = adapter

        var currency = '$'
        binding.currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    currency = currencies[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.addJobBtn.setOnClickListener {
            val title = binding.titleEt.text.toString()
            val description = binding.descriptionEt.text.toString()
            val benefits = binding.benefitsEt.text.toString()
            val salary = "$currency${binding.salaryEt.text}"
            val isNegotiable = binding.negotiableCb.isChecked
            if (title.isEmpty() || description.isEmpty() || benefits.isEmpty() ||
                binding.salaryEt.text.toString().isEmpty()
            )
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.visibility = View.VISIBLE
                binding.addJobBtn.visibility = View.INVISIBLE
                val id = intent.getStringExtra("companyId")!!
                val job = JobModel(title, description, benefits, salary, isNegotiable, id)
                addJob(job)
            }

        }
    }

    private fun addJob(job: JobModel) {
        db
            .collection("jobs")
            .add(job)
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                binding.addJobBtn.visibility = View.VISIBLE
                var builder = NotificationCompat.Builder(this, "Job")
                    .setSmallIcon(R.drawable.ic_job)
                    .setContentTitle("Job Added")
                    .setContentText("Job Added Successfully")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                with(NotificationManagerCompat.from(this)) {
                    notify(1, builder.build())
                }
            }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Job"
            val descriptionText = "Job Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Job", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}