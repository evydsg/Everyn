package com.everyn

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.everyn.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {

    //View Binding
    private lateinit var binding: ActivityLoginBinding

    //Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    //Request code for Google Sign-In intent
    private val RC_GOOGLE_SIGN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate layout via View Biding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //Set up all click listeners
        setupFooterLink()
        setupClickListeners()

    }

    //Click Listeners
    private fun setupClickListeners() {
        //Email/password sign-in
        binding.btnSignIn.setOnClickListener {
            if (validateInputs) {
                signInWithEmail()
            }
        }

        binding.btnGoogle.setOnClickListener {
            signInWithGoogle()
        }

        //Forgot password
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Enter your email above first"
                binding.etEmail.requestFocus()
            } else {
                sendPasswordReset(email)
            }
        }
    }

    //Footer link
    private fun setupFooterLink() {
        val fullText = "Don't have an account? Create one."
        val spannable = SpannableString(fullText)

        //Find the start/end of Create one
        val start = fullText.indexOf("Create one.")
        val end = start + "Create one.".length

        //Gold color for the link
        val goldColor = ContextCompat.getColor(this, R.color.text_primary)

        //Apply gold color span
        spannable.setSpan(
            ForegroundColorSpan(goldColor),
            start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //Apply clickable span - navigates to AccountCreationActivity
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@LoginActivity, AccountCreationActivity::class.java))
            }

            // Remove default underline from the clickable span
            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = goldColor
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //Attach spannable to TextView and enable link movement
        binding.tvCreateAccount.text = spannable
        binding.tvCreateAccount.movementMethod = LinkMovementMethod.getInstance()
        //Remove the highlight color on tap
        binding.tvCreateAccount.highlightColor = android.graphics.Color.TRANSPARENT

    }
    //Firebase - Email/Password Sign-In
    private fun signInWithEmail() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // TODO: Navigate to Wardrobe Home screen when built
                    showToast("Signed in successfully")
                } else {
                    showToast("Sign-in failed: ${task.exception?.message}")
                }
            }
    }

}