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

### Session — March 17, 2026

**Onboarding Flow (S-02) — Complete**

Finished S-02 implementation and resolved layout bugs during testing.

- Created `OnboardingActivity.kt` — wires `OnboardingAdapter` to ViewPager2, disables swipe, handles `onCtaClicked` and `onSkipClicked` callbacks, navigates to account creation on completion
- Registered `OnboardingActivity` in `AndroidManifest.xml`
- Fixed `fragment_onboarding.xml` bugs: `dash3` height corrected to `3dp`, CTA id cased to `onboardingCta`, button constraints moved to `app:` namespace, `textSize` corrected from `dp` to `sp`, skip link `constraintStart` fixed to `parent`
- Fixed `activity_onboarding.xml`: added missing `app:layout_constraintStart_toStartOf="parent"` on ViewPager2 — required for `0dp` width to resolve correctly

**Status:** Complete

### Session — March 19, 2026

**Create Account Screen (S-03) — In Progress**

Started implementation of the Create Account screen.

- Created `activity_create_account.xml` with ScrollView + ConstraintLayout host
- Added heading (Cormorant Garamond, 44sp) and italic gold subtitle
- Added "Continue with Google" gold pill button
- Started "or" divider row — left line, center label, right line in progress
- Added all required strings to `strings.xml`

**Status:** In progress — divider, input fields, CTA, and footer links next

### Session — March 20, 2026

**Create Account Screen (S-03) — In Progress**

Continued implementation of the Create Account screen.

- Completed `activity_create_account.xml` — full layout with heading, italic gold subtitle, Google CTA, or divider, all four input fields, Create Account button, sign in link, and terms footer
- Fixed 8 bugs caught during review: `app:` namespace typo on dividerOr, `android:font` → `android:fontFamily` across all labels and fields, `android:text` → `android:hint` on all EditTexts, `inputPassowrd` id typo, incorrect `inputType` on email/password fields, wrong namespace on tvSignIn constraint, cleaned up tvTerms stray attribute and added gravity center

**Status:** In progress — `bg_input_field.xml` drawable and `AccountCreationActivity.kt` next

### Session — March 26, 2026

**Create Account Screen (S-03) — In Progress**

Continued implementation of the Create Account screen.

- Added `colorSurface` and `colorGoldMuted` color tokens to both `values/colors.xml` and `values-night/colors.xml` — warm off-white/deep near-black for surface, desaturated gold for input borders
- Created `bg_input_field.xml` — rounded rectangle drawable with muted gold stroke and `colorSurface` fill, applied to all four input fields

**Status:** In progress — `AccountCreationActivity.kt` next; emulator split-screen issue ongoing

### Session — March 27, 2026

**Create Account Screen (S-03) — Completed**

Finished implementation of the Create Account screen.

- Completed `AccountCreationActivity.kt` — View Binding setup, click listeners for primary CTA,
  Google Sign-In CTA, and sign-in link, and full local validation (name, username, email format,
  password length); Firebase Auth stubbed with TODOs for next auth session

**Status:** S-03 complete — Firebase Auth screens next

### Session — April 1, 2026

**Firebase Setup — Completed**

Configured Firebase in the project.

- Created Firebase project and registered the Android app in the Firebase console
- Enabled Email/Password and Google Sign-In auth methods in Firebase Authentication
- Generated SHA-1 and SHA-256 fingerprints via `./gradlew signingReport` and added them to Firebase
- Downloaded and placed `google-services.json` in the `app/` directory
- Added Google Services plugin to project-level `build.gradle.kts`
- Added Firebase BOM (32.8.1), `firebase-auth`, and `play-services-auth` (20.7.0) to app-level `build.gradle.kts` — versions pinned for Kotlin 1.9.0 compatibility
- Resolved Kotlin metadata version mismatch by downgrading BOM from 34.11.0 to 32.8.1

**Status:** Firebase setup complete — wiring Firebase Auth into `AccountCreationActivity.kt` next
