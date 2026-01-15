> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 4 – New Automated Tests (Duplicates + Merge)

## Goal

Increase confidence in our chosen feature slice (**duplicate detection + merge**) by adding a small set of focused automated tests for gaps found in Phase 2.

We also fixed two OS-specific failing tests to improve reproducibility on macOS.

## What was added / changed

### 1) New tests added for the feature slice

- `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeTwoEntriesActionTest.java`
  - Verifies merge side effects: inserts merged entry, removes both originals, and creates an undo edit.
  - Related requirements: **R3** (safe merge operation), **R4** (user safety via undo support).

- `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeEntriesActionTest.java`
  - Verifies selection precondition: if the user does **not** select exactly two entries, the merge is not executed and an information dialog is shown.
  - Related requirements: **R4** (user control / safety).

- `jabgui/src/test/java/org/jabref/gui/duplicationFinder/DuplicateSearchResultTest.java`
  - Verifies bookkeeping for the “keep merge” path (two originals marked for removal, merged entry scheduled to be added).
  - Related requirements: **R1/R3** (duplicate handling + merge outcome).

### 2) One existing macOS-related test fixed (OS-aware expectation)

- Updated `jabgui/src/test/java/org/jabref/gui/push/PushToTeXworksTest.java`
  - On macOS, JabRef launches external apps via `open -a ... --args ...`, while other OSes use a direct executable call.
  - The test now expects the correct command format depending on the current OS.

### 3) One additional macOS-related test fixed (shortcut modifier mapping)

- Updated `jabgui/src/test/java/org/jabref/gui/keyboard/KeyBindingsTabModelTest.java`
  - On macOS, JavaFX treats the “shortcut” modifier as **Command (meta)**, not **Control**.
  - The test now builds the `KeyEvent` using the correct modifier for the current OS, making the assertion platform-independent.

## Commands to run (reproducible)

From the repo root:

- Run the new merge tests:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.mergeentries.threewaymerge.MergeTwoEntriesActionTest`
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.mergeentries.threewaymerge.MergeEntriesActionTest`

- Run the new duplicate-search bookkeeping test:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.duplicationFinder.DuplicateSearchResultTest`

- Run the macOS-fixed test:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.push.PushToTeXworksTest`
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.keyboard.KeyBindingsTabModelTest`

Success looks like: `BUILD SUCCESSFUL` and `0 failed` for the executed tests.

> Note (Apple Silicon/macOS): If JavaFX tests fail with `No toolkit found`, follow the arm64 setup in `docs/Assignmet_presentation/phase1-project-setup.md`.

## Screenshot checklist

See `docs/Assignmet_presentation/phase4-screenshots/README.md`.
