package com.everyn

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.everyn.databinding.ActivityCreateAccountBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AccountCreationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    //Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    //Google Sign-In Client
    private lateinit var googleSignInClient: GoogleSignInClient

    //Request code for Google Sign-In Intent
    companion object{
        private const val RC_SIGN_IN = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Inflate the layout using View Binding
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_create_account)
        setContentView(binding.root)

        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //Configure Google Sign-In to request the user's ID token
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setupClickListeners()
    }
    //Click Listeners
    private fun setupClickListeners (){
        //Primary CTA - validate inputs before attempting account creation
        binding.btnCreateAccount.setOnClickListener {
            if(validateInputs()){
                createAccountWithEmail()
            }
        }

        //Google Sign-In CTA
        binding.btnGoogle.setOnClickListener{
            //TODO: Wire up Google Sign-In via Firebase Auth in auth session
            signInWithGoogle()
        }

        //Sign in link goes back to LoginActivity placeholder
        binding.tvSignIn.setOnClickListener {
            //Replace with code to go back to LoginActivity
            finish()
        }
    }

    private fun createAccountWithEmail(){
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString()

        //Show feedback while request is in flight
        showToast("Create account...")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    //Account created, so navigate to the screen
                    showToast("Account created successfully!")
                }else{
                    showToast("Error: ${task.exception?.message}")
                }

            }

    }

    //Google SignIn
    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                //Google Sign-In succeeded
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch(e: ApiException){
                showToast("Google Sign-In failed: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                task ->
                if(task.isSuccessful){
                    //navigate to main screen
                    showToast("Google Sign-In successful")
                }else{
                    showToast("Firebase Auth error: ${task.exception?.message}")
                }
            }
    }

    //Input Validation
    private fun validateInputs(): Boolean{
        val name = binding.inputFullName.text.toString().trim()
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val confirmPassword = binding.inputConfirmPassword.text.toString().trim()

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