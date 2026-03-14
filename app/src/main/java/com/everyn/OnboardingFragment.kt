package com.everyn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.everyn.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    //Companion object holds the argument keys and factory method
    companion object{
        private const val ARG_SLIDE_INDEX = "slide_index"

        //Factory method - creates a fragment instance for the given slide position
        fun newInstance(slideIndex: Int) : OnboardingFragment{
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putInt(ARG_SLIDE_INDEX, slideIndex)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val slideIndex = arguments?.getInt(ARG_SLIDE_INDEX)?: 0

        //Populate heading, body, and CTA label based on slide index
        when(slideIndex){
            0 -> {
                binding.onboardingHeading.text = getString(R.string.onboarding_1_heading)
                binding.onboardingBody.text = getString(R.string.onboarding_1_body)
                binding.onboardingCTA.text = getString(R.string.onboarding_cta_continue)
            }
            1 -> {
                binding.onboardingHeading.text = getString(R.string.onboarding_2_heading)
                binding.onboardingBody.text = getString(R.string.onboarding_2_body)
                binding.onboardingCTA.text = getString(R.string.onboarding_cta_continue)
            }
            2 -> {
                binding.onboardingHeading.text = getString(R.string.onboarding_3_heading)
                binding.onboardingBody.text = getString(R.string.onboarding_3_body)
                binding.onboardingCTA.text = getString(R.string.onboarding_cta_create)
            }
        }

        //Update the progress dashes to reflect the active slide
        updateDashes(slideIndex)

        //CTA click - handled by the host activity via callback
        binding.onboardingCTA.setOnClickListener {
            (activity as? OnboardingActivity)?.onCtaClicked(slideIndex)
        }

        //Skip -- goes straight to account creation
        binding.onboardingSkip.setOnClickListener {
            (activity as? OnboardingActivity)?.onSkipClicked()
        }
    }

    //Sets the active dash to gold and the others to inactive gray
    private fun updateDashes(activeIndex: Int){
        val dashes = listOf(binding.dash1, binding.dash2, binding.dash3)
        val goldColor = resources.getColor(R.color.colorGold, requireContext().theme)
        val inactiveColor = resources.getColor(R.color.colorDashInactive, requireContext().theme)

        dashes.forEachIndexed{ index, dash ->
            dash.setBackgroundColor(if (index == activeIndex) goldColor else inactiveColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Avoid memory leaks by nulling the binding reference
        _binding = null
    }
}