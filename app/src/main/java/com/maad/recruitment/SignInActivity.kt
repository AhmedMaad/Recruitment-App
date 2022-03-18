package com.maad.recruitment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.databinding.ActivitySignInBinding
import com.maad.recruitment.jobseeker.AvailableCompaniesActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_in)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Sign In"
        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            else {
                binding.progress.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            binding.progress.visibility = View.INVISIBLE
                            //TODO: You have to get usertype before going anywhere
                            //startActivity(Intent(this, AvailableCompaniesActivity::class.java))
                            finish()
                        } else {
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
}