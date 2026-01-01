> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# A2 Presentation Guide (JabRef)

This file is a **slide-building guide** for the 5‑minute workshop presentation. It points to the most important evidence and suggests a simple slide flow.

## Where to find the important material

- **Phase 0 (project + scope + requirements):** `docs/Assignmet_presentation/phase0-project-selection.md`
- **Phase 1 (build/run/tests + environment issues):** `docs/Assignmet_presentation/phase1-project-setup.md`
  - Screenshots: `docs/Assignmet_presentation/phase1-screenshots/`
- **Phase 2 (existing tests analysis for duplicates/merge):** `docs/Assignmet_presentation/phase2-feature-slice-duplicate-merge.md`
  - Screenshot checklist: `docs/Assignmet_presentation/phase2-screenshots/README.md`
- **Phase 3 (exploratory testing):** `docs/Assignmet_presentation/phase3-exploratory-testing.md`
  - Screenshots: `docs/Assignmet_presentation/phase3-screenshots/`
- **Phase 4 (new automated tests + macOS fixes):** `docs/Assignmet_presentation/phase4-new-automated-tests.md`
  - Screenshots: `docs/Assignmet_presentation/phase4-screenshots/`
  - Presentation copy of tests: `docs/Assignmet_presentation/tests-written/`
- **Phase 5 (non-functional evaluation):** `docs/Assignmet_presentation/phase5-nonfunctional-evaluation.md`
  - Screenshots: `docs/Assignmet_presentation/phase5-screenshots/`

## Quick “mandatory requirements” checklist (what to cover on slides)

- **Project purpose + short description:** Phase 0
- **Requirements/spec overview (inferred):** Phase 0 (R1–R4)
- **Stakeholders + risks:** Phase 0 + Phase 5
- **Past / present / future development (maintenance evidence):** Phase 1 screenshots (GitHub Actions + Issues) + mention “active repo” from GitHub
- **Current testing strategy + tools:** Phase 1 + Phase 2 (JUnit/Mockito/TestFX, Gradle, GitHub Actions)
- **Automated test results:** Phase 1 screenshots + Phase 4 screenshots (our tests)
- **Exploratory testing method + findings:** Phase 3 + Phase 3 screenshots
- **Bug handling:** GitHub Issues + CI (Phase 1 screenshots)

## Slide outline (5 minutes)

Keep it small: ~7 slides is usually enough.

### Slide 1 — Title + project

- Project: **JabRef** (open-source citation/reference manager)
- Repo: https://github.com/JabRef/jabref
- Why it matters: widely used, actively maintained, GUI app + existing automated tests
- Stakeholders: researchers/students, maintainers, plugin/tooling users

### Slide 2 — Scope (feature slice)

- Feature slice: **duplicate detection + merge entries**
- Inferred requirements (R1–R4): duplicates detected, presented, merge safely, user control (cancel/undo)
- Risk focus: wrong merges can cause **data loss** or “silently” change references
- Evidence: `docs/Assignmet_presentation/phase0-project-selection.md`

### Slide 3 — Setup & tools

- Build/run: Gradle wrapper (`./gradlew`), Java toolchain (**JDK 24**)
- Test frameworks: JUnit 5, Mockito, TestFX (JavaFX), ArchUnit
- CI + bugs: GitHub Actions + GitHub Issues
- Setup gotcha (macOS Apple Silicon): JavaFX needs **arm64** Gradle JVM (Phase 1 “Common issues”)
- Evidence screenshots:
  - `docs/Assignmet_presentation/phase1-screenshots/02-build-success.png`
  - `docs/Assignmet_presentation/phase1-screenshots/04-ci-pipeline.png`
  - `docs/Assignmet_presentation/phase1-screenshots/05-issue-tracker.png`

### Slide 4 — Existing tests (what exists + what it covers)

- Duplicate logic: `DuplicateCheckTest` (unit-level)
- Merge support: merge view-model/helper tests in `jabgui`
- CI runs module tests on Linux + Xvfb (JavaFX)
- Key point: coverage exists, but **end-to-end GUI flows** are limited
- Evidence: `docs/Assignmet_presentation/phase2-feature-slice-duplicate-merge.md`

### Slide 5 — Exploratory testing (manual)

- Method: exploratory scenarios (happy path, edge cases, cancellation, invalid selection)
- Key finding to discuss: **cancel behavior** (documented in Phase 3)
- Evidence:
  - `docs/Assignmet_presentation/phase3-exploratory-testing.md`
  - Screenshots: `docs/Assignmet_presentation/phase3-screenshots/`

### Slide 6 — New automated tests (our contribution)

- Added tests for merge side-effects + selection safety + duplicate bookkeeping
- Fixed 2 macOS-sensitive tests so they match OS behavior (external app launch + shortcut modifier)
- Evidence:
  - `docs/Assignmet_presentation/phase4-new-automated-tests.md`
  - Screenshot(s): `docs/Assignmet_presentation/phase4-screenshots/01-new-tests-pass.png`, `docs/Assignmet_presentation/phase4-screenshots/02-macos-fix-tests-pass.png`
  - Code copy for presentation: `docs/Assignmet_presentation/tests-written/`

### Slide 7 — Non-functional evaluation + wrap-up

- Reliability: user confirmation + undo support reduces risk of data loss
- Performance: duplicate search is roughly **O(n²)** comparisons for large libraries
- Privacy: documented in `PRIVACY.md` (metadata lookups / update checks / optional telemetry)
- Evidence:
  - `docs/Assignmet_presentation/phase5-nonfunctional-evaluation.md`
  - Screenshot(s): `docs/Assignmet_presentation/phase5-screenshots/01-privacy-policy.png`

## Live demo (optional)

If you want a short live demo:

- Run app: `./gradlew run`
- Show menu: **Quality → Find duplicates** and **Quality → Merge entries**
- Show undo after merge: **Edit → Undo**

## Before final submission (quick checks)

- Ensure Phase 2 screenshots are taken if you want extra evidence: `docs/Assignmet_presentation/phase2-screenshots/README.md`
- Ensure `docs/Assignmet_presentation/tests-written/` is committed/pushed (it is a convenience copy for slides)

## What is left (Phase 6–7)

- Merge slides from both partners into one deck (avoid duplicates, keep terminology consistent).
- Rehearse: keep the “story” tight (scope → tests → what we learned), and prepare to answer “why this scope?” and “why these tests?”.
