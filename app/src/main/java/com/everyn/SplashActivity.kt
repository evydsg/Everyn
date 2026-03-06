package com.everyn

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.everyn.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //setContentView(R.layout.activity_splash)

        setupSignInLink()
        setupButtons()
    }
    private fun setupSignInLink(){
        var fulltext = getString(R.string.signin_prompt)
        val signInText = "Sign in"
        val spannable = SpannableString(fulltext)

        val start = fulltext.indexOf(signInText)
        val end = start + signInText.length

        //Already have an account? Stay Dim
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.text_dim)),
            0, start,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            object : ClickableSpan(){
                override fun onClick(widget: View){
                    onSignInClicked()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.color = ContextCompat.getColor(this@SplashActivity, R.color.gold_core)
                    ds.isUnderlineText = true
                }
            },
            start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvSignIn.text = spannable
        binding.tvSignIn.movementMethod = LinkMovementMethod.getInstance()

        //Removes the default blue hightlight when tapped
        binding.tvSignIn.highlightColor = android.graphics.Color.TRANSPARENT
    }

    //Button Click
    private fun setupButtons(){
        binding.btnGetStarted.setOnClickListener{
            onGetStartedClicked()
        }
    }

    //Navigation
    private fun onGetStartedClicked()
    {
        //Add code to screen
    }
    private fun onSignInClicked()
    {
        //Add code to add sign in button
    }

}