package com.rono.toTrade

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.panjikrisnayasa.bigproject.SignUpActivity
import com.rono.toTrade.databinding.ActivityLoginBinding
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    companion object {
        const val RC_SIGN_UP = 1
    }

    private lateinit var mAuth: FirebaseAuth
    lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        supportActionBar?.title = "Login to toTrade"

        loginBinding.tvLoginSignUpHere.setOnClickListener(this)
        loginBinding.btnLoginLogin.setOnClickListener(this)
        loginBinding.tietLoginPassword.addTextChangedListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_UP && resultCode == Activity.RESULT_OK) {
            val registeredEmail = data?.getStringExtra(SignUpActivity.EXTRA_EMAIL)
            registeredEmail?.let {
                loginBinding.tietLoginEmail.setText(it, TextView.BufferType.EDITABLE)
                loginBinding.tietLoginEmail.setSelection(it.length)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login_sign_up_here -> {
                val signUpIntent = Intent(this, SignUpActivity::class.java)
                startActivityForResult(signUpIntent, RC_SIGN_UP)
                clearLoginFields()
            }
            R.id.btn_login_login -> {
                var isNull = false
                var isEmailInvalid = false

                val email = loginBinding.tietLoginEmail.text.toString()
                val password = loginBinding.tietLoginPassword.text.toString()

                // Email address validation
                val pattern = Pattern.compile(
                    "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+",
                    Pattern.CASE_INSENSITIVE
                )
                val matcher = pattern.matcher(email)

                if (!email.isNotBlank()) {
                    isNull = true
                    loginBinding.tietLoginEmail.error = getString(R.string.login_error_null)
                } else if (!matcher.matches()) {
                    isEmailInvalid = true
                    loginBinding.tietLoginEmail.error =
                        getString(R.string.login_error_email_invalid)
                }
                if (!password.isNotBlank()) {
                    isNull = true
                    loginBinding.tietLoginPassword.error = getString(R.string.login_error_null)
                }

                if (!isNull && !isEmailInvalid) {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            val cUser = mAuth.currentUser
                            if (task.isSuccessful) {
                                if (!cUser?.isEmailVerified!!) {
                                    Toast.makeText(
                                        this,
                                        "You need to verify your email address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    mAuth.signOut()
                                } else {
                                    val homeIntent = Intent(this, MainActivity::class.java)
                                    startActivity(homeIntent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Email or password incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    private fun clearLoginFields() {
        loginBinding.tietLoginEmail.text?.clear()
        loginBinding.tietLoginEmail.error = null
        loginBinding.tietLoginPassword.text?.clear()
        loginBinding.tietLoginPassword.error = null
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Handle any pre-text change logic here
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Handle any text change logic here
    }
}
