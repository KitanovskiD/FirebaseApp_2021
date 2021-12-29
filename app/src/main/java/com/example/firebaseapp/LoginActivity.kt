package com.example.firebaseapp

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email: EditText = findViewById(R.id.etUserName)
        val password: EditText = findViewById(R.id.etPassword)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)


        loginButton.setOnClickListener {
            // do login
            val emailValue: String = email.text.toString()
            val passwordValue: String = password.text.toString()

            if(emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }else {
                login(emailValue, passwordValue)
            }
        }

        registerButton.setOnClickListener {
            val emailValue: String = email.text.toString()
            val passwordValue: String = password.text.toString()

            if(emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }else {
                register(emailValue, passwordValue)
            }
        }

    }

    private fun register(emailValue: String, passwordValue: String) {
        mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Registration success.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                   Toast.makeText(
                       this, "Registration failed.",
                       Toast.LENGTH_SHORT
                   ).show()
                }
            }
    }

    private fun login(emailValue: String, passwordValue: String) {
        mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    navigateOut()
                } else {
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if(checkIfLoggedIn()) {
            navigateOut()
        }
    }

    private fun checkIfLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    private fun navigateOut() {
        val intent:  Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}