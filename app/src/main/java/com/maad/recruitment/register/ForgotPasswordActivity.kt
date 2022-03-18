package com.maad.recruitment.register

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.recruitment.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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