package com.everyn

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    //Total number of onboarding slides
    override fun getItem() : Int = 3

    //Returns the correct fragment for each slide position
    override fun createFragment(position: Int): Fragment {
        return OnboardingFragment.newInstance(position)
    }
}