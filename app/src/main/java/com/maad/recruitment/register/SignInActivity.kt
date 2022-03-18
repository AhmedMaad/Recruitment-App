package com.maad.recruitment.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.company.CompanyProfileActivity
import com.maad.recruitment.databinding.ActivitySignInBinding
import com.maad.recruitment.jobseeker.AvailableCompaniesActivity
import com.maad.recruitment.recruiter.AvailableEmployeesActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Sign In"
        auth = Firebase.auth
        db = Firebase.firestore

        binding.signInBtn.setOnClickListener {
            val email = "j@gmail.com"
            val password = "123456"
            //TODO: Uncomment this after debugging phase
            //val email = binding.emailEt.text.toString()
            //val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            else {
                binding.signInBtn.visibility = View.INVISIBLE
                binding.progress.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful)
                            getUserType(task.result!!.user!!.uid)
                        else {
                            binding.signInBtn.visibility = View.INVISIBLE
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        binding.registerAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding.forgotPassword.setOnClickListener {
            //show dialog with email edit text
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun getUserType(docId: String) {
        db
            .collection("users")
            .document(docId)
            .get()
            .addOnSuccessListener {
                binding.signInBtn.visibility = View.VISIBLE
                binding.progress.visibility = View.INVISIBLE

                val prefs = getSharedPreferences("authentication", MODE_PRIVATE).edit()
                prefs.putString("id", docId)
                prefs.putString("email", binding.emailEt.text.toString())
                prefs.apply()

                //The case of "else" represents "Recruiter"
                val activityToOpen = when (it.getString("userType")) {
                    "Job Seeker" -> AvailableCompaniesActivity::class.java
                    "Company" -> CompanyProfileActivity::class.java
                    else -> AvailableEmployeesActivity::class.java
                }
                startActivity(Intent(this, activityToOpen))
                finish()
            }

    }
}