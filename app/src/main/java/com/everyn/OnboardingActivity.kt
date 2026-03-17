package com.everyn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everyn.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Wire up updater
        binding.onboardingViewPager.adapter = OnboardingAdapter(this)

        //Disable swiping - navigation is button-driver only
        binding.onboardingViewPager.isUserInputEnabled = false
    }

    //Called by the fragment CTA - advances the pager or launches account creation
    fun onCtaClicked(currentSlide: Int){
        if (currentSlide < 2){
            binding.onboardingViewPager.currentItem = currentSlide + 1

        }else{
            navigateToAccountCreation()
        }
    }

    fun onSkipClicked(){
        navigateToAccountCreation()
    }

    private fun navigateToAccountCreation(){
        //Open AccountCreation Page
        val intent = Intent(this, AccountCreationActivity::class.java)
        startActivity(intent)
        finish()
    }
}