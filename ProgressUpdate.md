## Progress Log

### Session 01 — Mar 7, 2025
**S-01 Splash Screen — Complete**
- Project setup: Android Studio Ladybug, Empty Views Activity, Kotlin
- Design system foundation: full color palette, typography (Cormorant Garamond + Outfit), button drawables
- Dark mode foundation: semantic color system with `values-night` automatic switching
- View Binding enabled
- Splash screen fully implemented: wordmark, slogan, gold CTA, clickable sign-in link
- Resolved AGP 8.7.2 / Gradle 8.9 / SDK 35 compatibility stack

### Session — March 10, 2026

**Onboarding Flow (S-02)**

Began implementation of the 3-slide onboarding flow.

- Created `activity_onboarding.xml` as the ViewPager2 host screen
- Started `fragment_onboarding.xml` as the reusable slide template — covers heading, body copy, gold pill CTA, and skip link
- Currently refining the progress dash indicator row — working through active/inactive color values for light (`#CCCCCC`) and dark mode (`#3A3A3A`)

**Status:** In progress

### Session — March 14, 2026

**Onboarding Flow (S-02) — Step 2 In Progress**

Continued S-02 implementation, working through Kotlin files one at a time.

- Created `OnboardingAdapter.kt` — `FragmentStateAdapter` wired to ViewPager2, returns `OnboardingFragment` per position, 3 slides total
- Created `OnboardingFragment.kt` — reusable fragment driven by slide index; populates heading, body, and CTA label from strings; progress dash logic updates active/inactive colors per slide; CTA and skip callbacks delegate to host activity

**Status:** In progress — `OnboardingActivity.kt` next