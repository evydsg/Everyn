# Everyn
### *dressed like you meant it*

Everyn is an Android app that helps people build outfits from their wardrobe. Add items via product links, get AI-powered outfit suggestions, and discover missing pieces — filtered by material and budget.

---

## Features

- **Link-based item import** — paste any product URL and Everyn scrapes the name, price, material, color, and brand automatically
- **Wardrobe management** — browse and filter your items by material and budget
- **Outfit builder** — tap pieces to assemble looks, with live AI match scoring and total cost
- **Smart search** — describe what you're missing in natural language; results are personalized to your wardrobe gaps
- **AI outfit ideas** — Claude-powered combinations scored by compatibility, with style commentary
- **Style preferences** — set preferred materials, budget range, and style vibe to personalize all AI features

---

## Tech Stack

| Layer | Technology |
|---|---|
| Mobile | Kotlin + Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Navigation | Jetpack Navigation Component |
| Networking | Retrofit + OkHttp |
| Local DB | Room (wardrobe items, saved outfits) |
| Remote DB | Supabase (user accounts, sync) |
| AI | Claude API (outfit scoring, NL search, gap analysis) |
| Link scraping | Backend via Playwright/Puppeteer |
| Backend | Node.js or FastAPI |
| Auth | Supabase Auth |
| Image loading | Coil |

---

## Project Structure

```
everyn/
├── app/
│   └── src/main/
│       ├── java/com/everyn/
│       │   ├── data/
│       │   │   ├── local/          # Room database, DAOs, entities
│       │   │   ├── remote/         # API services, Supabase client
│       │   │   └── repository/     # Repository implementations
│       │   ├── domain/
│       │   │   ├── model/          # WardrobeItem, Outfit, UserPrefs
│       │   │   └── usecase/        # Business logic
│       │   └── ui/
│       │       ├── wardrobe/       # Wardrobe screen + item detail sheet
│       │       ├── build/          # Outfit builder screen
│       │       ├── search/         # Search & discovery screen
│       │       ├── ideas/          # AI ideas screen
│       │       ├── profile/        # Profile & preferences
│       │       ├── onboarding/     # Splash + onboarding flow
│       │       └── theme/          # Design tokens, colors, typography
│       └── res/
├── backend/
│   ├── scraper/                    # Product link scraping service
│   └── api/                        # REST endpoints
└── README.md
```

---

## Design System

**Colors**

| Token | Hex | Usage |
|---|---|---|
| `bg/base` | `#080706` | App background |
| `bg/surface` | `#201D1A` | Cards, panels |
| `bg/raised` | `#2A2520` | Elevated surfaces |
| `border/default` | `#353028` | All borders |
| `accent/gold` | `#C9A050` | Primary accent, CTAs |
| `accent/gold-bright` | `#E2B96A` | Active states, highlights |
| `accent/burgundy` | `#8B1A34` | Secondary accent |
| `text/primary` | `#F4EDE0` | Headings, key info |
| `text/secondary` | `#A89880` | Body text |
| `text/dim` | `#6B5C48` | Labels, metadata |

**Typography**

| Role | Font | Weight | Size |
|---|---|---|---|
| Display / headings | Cormorant Garamond | Light 300 | 28–52sp |
| Card titles | Cormorant Garamond | Regular 400 | 14–20sp |
| Body | Outfit | Light 300 | 13–14sp |
| Labels / ALL CAPS | Outfit | Medium 500 | 9–11sp |
| Prices | Outfit | Regular 400 | 12–14sp |

Both fonts are available via Google Fonts. Add to `res/font/` or use the Downloadable Fonts API.

**Spacing** — 8pt grid: 4, 8, 12, 16, 20, 28, 36, 48dp

**Corner radius** — Tags: 4dp · Inputs: 8dp · Cards: 14dp · Panels: 20dp · Pills/CTAs: 100dp

---

## Screens

| ID | Screen | Nav Tab |
|---|---|---|
| S-01 | Splash / Launch | — |
| S-02 | Onboarding Step 1 | — |
| S-03 | Onboarding Step 4 | — |
| S-04 | Wardrobe (Home) | ◫ Wardrobe |
| S-05 | Item Detail (Bottom Sheet) | ◫ Wardrobe |
| S-06 | Build Outfit | ⊕ Build |
| S-07 | Search & Discover | ⌖ Search |
| S-08 | AI Outfit Ideas | ✦ Ideas |
| S-09 | Profile & Preferences | ○ Profile |

---

## AI Integration (Claude API)

Three main AI touchpoints, all via the Anthropic Claude API:

**1. Outfit Scoring**
When a user assembles pieces in the Build screen, the app sends item metadata (material, color, type, brand) to the Claude API and returns a compatibility score (0–100) plus a one-line style note.

**2. Gap Analysis / Smart Search**
On the Search screen, the user's natural language query plus their wardrobe contents are sent to Claude. It identifies missing categories, recommends materials, and suggests items to look for.

**3. Idea Generation**
The Ideas screen sends the full wardrobe inventory to Claude, which returns 3–6 outfit combinations with names, vibes, scores, and style commentary.

Suggested Claude model: `claude-sonnet-4-6`

---

## Link Scraping Service

The backend scraping service accepts a product URL and returns structured item data. Run it as a microservice alongside the main API.

**Input:** `{ "url": "https://..." }`

**Output:**
```json
{
  "name": "Slim Trousers",
  "brand": "Arket",
  "price": 120,
  "currency": "USD",
  "material": "100% Cotton",
  "color": "Dark Navy",
  "category": "bottom",
  "imageUrl": "https://...",
  "sourceUrl": "https://..."
}
```

Libraries: Playwright (JS) or Playwright + BeautifulSoup (Python). For robustness, fall back to an LLM parse of raw HTML when structured selectors fail.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17+
- Android SDK 34 (target), SDK 26 (min)
- Node.js 18+ (for backend)
- A Supabase project
- An Anthropic API key

### Setup

```bash
# Clone the repo
git clone https://github.com/your-username/everyn.git
cd everyn

# Backend setup
cd backend
npm install
cp .env.example .env
# Fill in ANTHROPIC_API_KEY and SUPABASE_URL/KEY in .env
npm run dev

# Android
# Open /app in Android Studio
# Add to local.properties:
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_ANON_KEY=your-anon-key
ANTHROPIC_API_KEY=your-api-key   # route through backend, don't expose in app
```

> **Note:** Never embed the Anthropic API key directly in the Android app. All Claude API calls should be proxied through your backend.

---

## Slogan Options

| Slogan | Tone |
|---|---|
| **dressed like you meant it** ← recommended | Confident, universal |
| luxury is knowing what to wear | Aspirational, inclusive |
| your closet has been lying to you | Bold, social-ready |
| wear it like it's yours | Empowering |

---

## Roadmap

- [ ] Core wardrobe CRUD + Room DB
- [ ] Link scraper microservice
- [ ] Wardrobe screen with filters
- [ ] Build outfit screen with AI scoring
- [ ] Search screen with NL query
- [ ] AI Ideas screen
- [ ] Onboarding flow
- [ ] Profile + style preferences
- [ ] Supabase sync + auth
- [ ] App icon + splash screen
- [ ] Play Store release

---

## License

Private — all rights reserved.
