package com.maad.recruitment.recruiter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.maad.recruitment.ExitDialog
import com.maad.recruitment.databinding.ActivityAvailableSeekersBinding
import com.maad.recruitment.jobseeker.SeekerModel

class AvailableSeekersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableSeekersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableSeekersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        db
            .collection("seekers")
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                val seekers = it.toObjects(SeekerModel::class.java) as ArrayList<SeekerModel>
                val adapter = SeekersAdapter(this, seekers)
                binding.seekersRv.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.INVISIBLE
            }

    }

    override fun onBackPressed() {
        val exit = ExitDialog()
        exit.isCancelable = false
        exit.show(supportFragmentManager, null)
    }

}