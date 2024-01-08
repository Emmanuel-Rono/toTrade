package com.panjikrisnayasa.bigproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rono.toTrade.LoginActivity
import com.rono.toTrade.R
import com.rono.toTrade.dataStructures.reviews.User
import com.rono.toTrade.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUserDatabaseReference: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Sign Up to Appskie"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvSignUpLoginHere.setOnClickListener(this)
        binding.btnSignUpSignUp.setOnClickListener(this)
        binding.tietSignUpPassword.addTextChangedListener(this)

        mAuth = FirebaseAuth.getInstance()
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Set OnClickListener for password visibility
        binding.ivSignUpTogglePassword.setOnClickListener {
            // Toggle password visibility
            val isPasswordVisible = binding.tietSignUpPassword.transformationMethod == null
            binding.tietSignUpPassword.transformationMethod =
                if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()

            // Change the icon accordingly
            binding.ivSignUpTogglePassword.setImageResource(
                if (isPasswordVisible) R.drawable.ic_password_visible
                else R.drawable.ic_password_hidden
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_sign_up_login_here -> {
                finish()
            }
            R.id.btn_sign_up_sign_up -> {
                var isNull = false
                var isPasswordUnsafe = false
                var isEmailInvalid = false

                val fullName = binding.tietSignUpFullName.text.toString()
                val email = binding.tietSignUpEmail.text.toString()
                val password = binding.tietSignUpPassword.text.toString()

                //email address validation
                val pattern = Pattern.compile(
                    "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+",
                    Pattern.CASE_INSENSITIVE
                )
                val matcher = pattern.matcher(email)

                if (!fullName.isNotBlank()) {
                    isNull = true
                    binding.tietSignUpFullName.error = getString(R.string.sign_up_error_null)
                }

                if (!email.isNotBlank()) {
                    isNull = true
                    binding.tietSignUpEmail.error = getString(R.string.sign_up_error_null)
                } else if (!matcher.matches()) {
                    isEmailInvalid = true
                    binding.tietSignUpEmail.error =
                        getString(R.string.sign_up_error_email_invalid)
                }

                if (!password.isNotBlank()) {
                    isNull = true
                    binding.tietSignUpPassword.error = getString(R.string.sign_up_error_null)
                } else if (password.length < 6) {
                    isPasswordUnsafe = true
                    binding.tietSignUpPassword.error =
                        getString(R.string.sign_up_error_password_unsafe)
                }

                if (!isNull && !isPasswordUnsafe && !isEmailInvalid) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val cUser = mAuth.currentUser
                                if (cUser != null) {
                                    val user = User(cUser.uid, fullName, email)
                                    mUserDatabaseReference.child(cUser.uid).setValue(user)

                                    cUser.sendEmailVerification().addOnCompleteListener { that ->
                                        if (that.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Verification email sent",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            mAuth.signOut()
                                            val loginIntent = Intent(this, LoginActivity::class.java)
                                            loginIntent.putExtra(EXTRA_EMAIL, email)
                                            setResult(Activity.RESULT_OK, loginIntent)
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Failed to send verification email",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Sign up failed, please try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Set password visibility toggle to true when text changes
        binding.tietSignUpPassword.isPasswordVisibilityToggleEnabled = true
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
