> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 1 – Project Setup, Build, Run, and Test (JabRef)

## 1) Prerequisites (from README + JabRef dev docs)

- **OS:** JabRef is cross-platform (**Windows / macOS / Linux**). For running GUI-related tests, a working desktop environment is required (or a virtual display on Linux).
- **Java:** **JDK 24** (see `.sdkmanrc` and `build-logic/src/main/kotlin/org.jabref.gradle.feature.compile.gradle.kts`).
  - **CI uses:** **Amazon Corretto 24** (`.github/workflows/tests-code.yml`).
  - You still need a local JVM (Java **17+**) to run the Gradle wrapper; Gradle will provision required toolchains automatically.
- **Build tool:** **Gradle (via wrapper)** — use `./gradlew` (macOS/Linux) or `gradlew.bat` (Windows). No separate Gradle install required.
- **Git:** required, including submodules (JabRef uses git submodules for CSL styles/locales and journal abbreviations).

## 2) One-time repo setup

1. Clone (if needed):
   - `git clone --recurse-submodules https://github.com/JabRef/jabref.git`
   - `cd jabref`

2. If you already cloned without submodules:
   - `git submodule update --init --recursive`

## 3) Exact commands

### a) Build the project

- **Quick build (no tests):** `./gradlew assemble`
  - Success looks like: `BUILD SUCCESSFUL`
  - Example artifacts:
    - `jabgui/build/distributions/` (ZIP/TAR distributions)
    - `jabgui/build/libs/` (JARs)

- **Full build (includes tests/checks):** `./gradlew build`
  - Success looks like: `BUILD SUCCESSFUL`

### b) Run the JabRef GUI application

- **Run from Gradle:** `./gradlew run`
  - Equivalent: `./gradlew :jabgui:run`
  - Success looks like: the **JabRef** main window opens and the Gradle task keeps running until you close the app.

### c) Run the existing automated tests

- **Run all tests (recommended CI-like mode):** `CI=true ./gradlew test`
  - Success looks like: `BUILD SUCCESSFUL` and a test summary with `0 failed`
  - Reports:
    - `jablib/build/reports/tests/test/index.html`
    - `jabgui/build/reports/tests/test/index.html`

- **Linux (headless) running GUI tests:** `CI=true xvfb-run --auto-servernum ./gradlew test`
  - This mirrors JabRef’s GitHub Actions setup (`.github/workflows/tests-code.yml`).

- **Run module test suites (useful if `:jabgui:test` is OS-sensitive):**
  - `CI=true ./gradlew :jablib:test`
  - `CI=true ./gradlew :jabgui:test`

## 4) Project technical summary

- **Primary language:** Java (multi-module Gradle project).
- **Build scripts / build logic:** Kotlin DSL (`build.gradle.kts`, `settings.gradle.kts`, `build-logic/**`).
- **Build tool:** Gradle wrapper (`./gradlew`).
- **Test frameworks/libraries (examples found in Gradle build files):**
  - JUnit (Jupiter + Platform)
  - Mockito
  - TestFX (JavaFX GUI testing)
  - ArchUnit
- **Test locations (examples):**
  - `jablib/src/test/java`
  - `jabgui/src/test/java`
  - `jabls/src/test/java`
  - `jabsrv/src/test/java`
  - `jabkit/src/test/java`
  - Shared utilities: `test-support/src/main/java`
- **CI system:** GitHub Actions (`.github/workflows/tests-code.yml` and other workflows under `.github/workflows/`).
- **Bug reports:** GitHub issue tracker: https://github.com/JabRef/jabref/issues

## 5) Common build/test issues (and minimal fixes)

- **Submodules missing** (common symptom: missing CSL styles/locales or journal abbreviation data):
  - Fix: `git submodule update --init --recursive`

- **Apple Silicon + wrong Java architecture** (common symptom: JavaFX tests fail with `No toolkit found` / `no suitable pipeline found`):
  - Fix: run Gradle using an **arm64** JDK (so JavaFX natives match):
    - `JAVA_HOME=$(/usr/libexec/java_home -v 24) CI=true ./gradlew test`
  - Verify: `./gradlew --version` should show `OS: ... aarch64` on Apple Silicon.

- **Headless Linux** (common symptom: JavaFX tests fail due to no display):
  - Fix: install Xvfb and run: `CI=true xvfb-run --auto-servernum ./gradlew test`

- **Some GUI tests can be OS-sensitive** (common symptom: `:jabgui:test` fails on one OS but passes in CI):
  - Fix: run tests in the same environment as CI (Linux + Xvfb): `CI=true xvfb-run --auto-servernum ./gradlew test`
  - Alternative: run non-GUI-heavy tests only: `CI=true ./gradlew :jablib:test`

- **Local tests flaky/slow due to parallel forks**:
  - Fix: set `CI=true` to disable local parallel test forks: `CI=true ./gradlew test`

## 6) Screenshot checklist (suggested location: `docs/Assignmet_presentation/phase1-screenshots/`)

- **GUI running:** JabRef main window after `./gradlew run`
- **Successful build output:** terminal showing `./gradlew assemble` (or `./gradlew build`) ending with `BUILD SUCCESSFUL`
- **Test execution output:** terminal showing `CI=true ./gradlew test` (or `CI=true ./gradlew :jablib:test`) ending with `BUILD SUCCESSFUL`
- **CI pipeline page:** GitHub Actions workflow “Source Code Tests” (`.github/workflows/tests-code.yml`) on https://github.com/JabRef/jabref/actions
- **Issue tracker page:** https://github.com/JabRef/jabref/issues
