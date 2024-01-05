package com.panjikrisnayasa.bigproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rono.toTrade.LoginActivity
import com.rono.toTrade.R
import com.rono.toTrade.dataStructures.reviews.User
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUserDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.title = "Sign Up to Appskie"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tv_sign_up_login_here.setOnClickListener(this)
        btn_sign_up_sign_up.setOnClickListener(this)
        tiet_sign_up_password.addTextChangedListener(this)

        mAuth = FirebaseAuth.getInstance()
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
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

                val fullName = tiet_sign_up_full_name.text.toString()
                val email = tiet_sign_up_email.text.toString()
                val password = tiet_sign_up_password.text.toString()

                //email address validation
                val pattern = Pattern.compile(
                    "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+",
                    Pattern.CASE_INSENSITIVE
                )
                val matcher = pattern.matcher(email)

                if (!fullName.isNotBlank()) {
                    isNull = true
                    tiet_sign_up_full_name.error = getString(R.string.sign_up_error_null)
                }

                if (!email.isNotBlank()) {
                    isNull = true
                    tiet_sign_up_email.error = getString(R.string.sign_up_error_null)
                } else if (!matcher.matches()) {
                    isEmailInvalid = true
                    tiet_sign_up_email.error = getString(R.string.sign_up_error_email_invalid)
                }

                if (!password.isNotBlank()) {
                    isNull = true
                    til_sign_up_password.isPasswordVisibilityToggleEnabled = false
                    tiet_sign_up_password.error = getString(R.string.sign_up_error_null)
                } else if (password.length < 6) {
                    isPasswordUnsafe = true
                    til_sign_up_password.isPasswordVisibilityToggleEnabled = false
                    tiet_sign_up_password.error = getString(R.string.sign_up_error_password_unsafe)
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
                                Toast.makeText(this, "Sign up failed, please try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        til_sign_up_password.isPasswordVisibilityToggleEnabled = true
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
