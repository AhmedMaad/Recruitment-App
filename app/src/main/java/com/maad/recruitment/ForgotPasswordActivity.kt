package com.maad.recruitment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : Activity() {

    //private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        //val view = binding.root
        //setContentView(view)

        val view = layoutInflater.inflate(R.layout.activity_forgot_password, null)
        val builder = AlertDialog.Builder(this)
        builder
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> finish() }
            .setCancelable(false)
            .setPositiveButton("Send") { _, _ ->
                val emailET: EditText = view.findViewById(R.id.forgot_password_et)
                val emailAddress = emailET.text.toString()
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        finish()
                    }
            }
            .show()

    }
}