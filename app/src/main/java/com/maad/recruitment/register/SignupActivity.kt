package com.maad.recruitment.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Sign Up"
        auth = Firebase.auth

        binding.signupBtn.setOnClickListener {
            val fName = binding.firstNameEt.text.toString()
            val lName = binding.lastNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val conPassword = binding.confirmPasswordEt.text.toString()

            val chosenRB: RadioButton = findViewById(binding.userTypeRg.checkedRadioButtonId)
            val userType = chosenRB.text.toString()

            if (password != conPassword) {
                binding.passwordEt.error = "Passwords don't match"
                binding.confirmPasswordEt.error = "Passwords don't match"
            } else {
                binding.signupBtn.visibility = View.INVISIBLE
                binding.progress.visibility = View.VISIBLE
                registerUser(email, password, fName, lName, userType)
            }
        }

        binding.alreadyUserTv.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

    }

    private fun registerUser(
        email: String,
        password: String,
        fName: String,
        lName: String,
        userType: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && auth.currentUser != null) {
                    val userId = auth.currentUser!!.uid
                    createUserInCloudFirestore(userId, fName, lName, userType, email)
                } else {
                    binding.signupBtn.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(
                        this,
                        "Error: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }

    private fun createUserInCloudFirestore(
        userId: String,
        fName: String,
        lName: String,
        userType: String,
        email: String
    ) {
        val user = User(userId, fName, lName, userType, email)
        val db = Firebase.firestore
        db
            .collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                binding.signupBtn.visibility = View.VISIBLE
                binding.progress.visibility = View.INVISIBLE
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
    }

}