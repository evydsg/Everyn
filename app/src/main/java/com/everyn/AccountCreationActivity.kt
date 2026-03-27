package com.everyn

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.everyn.databinding.ActivityCreateAccountBinding

class AccountCreationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Inflate the layout using View Binding
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_create_account)

        setupClickListeners()
    }
    //Click Listeners
    private fun setupClickListeners (){
        //Primary CTA - validate inputs before attempting account creation
        binding.btnCreateAccount.setOnClickListener {
            if(validateInputs()){
                //TODO: Replace with Firebase Auth Call in auth session
                showToast("Validation passed - Firebase coming soon!")
            }
        }

        //Google Sign-In CTA
        binding.btnGoogle.setOnClickListener{
            //TODO: Wire up Google Sign-In via Firebase Auth in auth session
            showToast("Google Sign-In coming soon!")
        }
    }

    //Input Validation
    private fun validateInputs(): Boolean{
        val name = binding.inputFullName.text.toString().trim()
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.toString().trim()
        val confirmPassword = binding.inputConfirmPassword.toString().trim()

        //Full name cannot be empty
        if(TextUtils.isEmpty(name)){
            binding.inputFullName.error = "Please enter your name"
            binding.inputFullName.requestFocus()
            return false
        }

        if(TextUtils.isEmpty(email) ||  !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.inputEmail.error = "Please enter a valid email address"
            binding.inputEmail.requestFocus()
            return false
        }

        if (password.length < 8){
            binding.inputPassword.error = "Password must be at least 8 characters."
            binding.inputPassword.requestFocus()
            return false
        }
        if (confirmPassword.length < 8){
            binding.inputConfirmPassword.error = "Password must be at least 8 characters."
            binding.inputConfirmPassword.requestFocus()
            return false
        }
        if(password != confirmPassword){
            binding.inputPassword.error = "Password and confirm password don't match."
            binding.inputPassword.requestFocus()
            return false
        }

        return true
    }

    // Helpers
    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}