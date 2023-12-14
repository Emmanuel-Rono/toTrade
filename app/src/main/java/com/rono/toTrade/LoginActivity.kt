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
import com.google.firebase.auth.FirebaseAuth
import com.panjikrisnayasa.bigproject.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    companion object {
        const val RC_SIGN_UP = 1
    }

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = "Login to toTrade"

        tv_login_sign_up_here.setOnClickListener(this)
        btn_login_login.setOnClickListener(this)
        tiet_login_password.addTextChangedListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_UP) {
            if (resultCode == Activity.RESULT_OK) {
                val registeredEmail = data?.getStringExtra(SignUpActivity.EXTRA_EMAIL)
                if (registeredEmail != null) {
                    tiet_login_email.setText(registeredEmail, TextView.BufferType.EDITABLE)
                    tiet_login_email.setSelection(registeredEmail.length)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login_sign_up_here -> {
                val signUpIntent = Intent(this, SignUpActivity::class.java)
                startActivityForResult(signUpIntent, RC_SIGN_UP)
                tiet_login_email.text?.clear()
                tiet_login_email.error = null
                tiet_login_password.text?.clear()
                tiet_login_password.error = null
            }
            R.id.btn_login_login -> {
                var isNull = false
                var isEmailInvalid = false

                val email = tiet_login_email.text.toString()
                val password = tiet_login_password.text.toString()

                //email address validation
                val pattern = Pattern.compile(
                    "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+",
                    Pattern.CASE_INSENSITIVE
                )
                val matcher = pattern.matcher(email)

                if (!email.isNotBlank()) {
                    isNull = true
                    tiet_login_email.error = getString(R.string.login_error_null)
                } else if (!matcher.matches()) {
                    isEmailInvalid = true
                    tiet_login_email.error = getString(R.string.login_error_email_invalid)
                }
                if (!password.isNotBlank()) {
                    isNull = true
                    til_login_password.isPasswordVisibilityToggleEnabled = false
                    tiet_login_password.error = getString(R.string.login_error_null)
                }

                if (!isNull && !isEmailInvalid) {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            val cUser = mAuth.currentUser
                            if (it.isSuccessful) {
                                if (!cUser?.isEmailVerified!!) {
                                    Toast.makeText(
                                        this,
                                        "You need to verify your email address",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    mAuth.signOut()
                                } else {
                                    val homeIntent = Intent(this, HomeActivity::class.java)
                                    startActivity(homeIntent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Email or password incorrect",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        til_login_password.isPasswordVisibilityToggleEnabled = true
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
