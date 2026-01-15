> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 2 – Existing Tests Analysis: Duplicate Detection & Merge

## Goal

Connect the chosen feature slice (“Duplicate detection and merge functionality”) to:
- the **user workflow** in the JabRef GUI,
- the **implementation locations** (modules/classes),
- the **existing automated tests** already in the repository,
- and assess how well the existing tests cover the slice.

This phase does **not** add new tests. Any “missing tests” listed here are inputs for **Phase 4**.

## 1) User workflow (GUI)

### A. Find duplicates (library-wide)

1. Open a library (a `.bib` file).
2. Go to **Quality → Find duplicates**.
3. JabRef iterates through pairs and shows a resolver dialog for each duplicate pair.
4. Choose an action, e.g.:
   - **Keep left / Keep right**
   - **Keep both**
   - **Merge** (keeps a merged entry)
   - **Cancel / break**

Official help page (linked from the app): https://docs.jabref.org/finding-sorting-and-cleaning-entries/findduplicates

### B. Merge two selected entries (manual merge)

1. In the entry table, select **exactly two** entries.
2. Go to **Quality → Merge entries**.
3. Review the merge dialog and confirm, or cancel.

## 2) Where this feature is implemented (code map)

### Duplicate detection logic (shared / non-GUI)

- `jablib/src/main/java/org/jabref/logic/database/DuplicateCheck.java`
  - Core duplicate decision method: `DuplicateCheck.isDuplicate(...)`

### Find duplicates (GUI command + dialog)

- Menu wiring:
  - `jabgui/src/main/java/org/jabref/gui/frame/MainMenu.java` (adds “Quality → Find duplicates”)
  - `jabgui/src/main/java/org/jabref/gui/actions/StandardActions.java` (`FIND_DUPLICATES`)
- Implementation:
  - `jabgui/src/main/java/org/jabref/gui/duplicationFinder/DuplicateSearch.java`
  - `jabgui/src/main/java/org/jabref/gui/duplicationFinder/DuplicateResolverDialog.java`

### Merge entries (GUI command + data update)

- Menu wiring:
  - `jabgui/src/main/java/org/jabref/gui/frame/MainMenu.java` (adds “Quality → Merge entries”)
- Implementation:
  - `jabgui/src/main/java/org/jabref/gui/mergeentries/threewaymerge/MergeEntriesAction.java` (entry selection, dialog, cancel vs merge)
  - `jabgui/src/main/java/org/jabref/gui/mergeentries/threewaymerge/MergeTwoEntriesAction.java` (inserts merged entry + removes originals)
  - Merge support utilities:
    - `jabgui/src/main/java/org/jabref/gui/mergeentries/**`

### Duplicate handling during import (related path)

- `jabgui/src/main/java/org/jabref/gui/externalfiles/ImportHandler.java`
  - Has duplicate-handling decisions using `DuplicateResolverDialog.DuplicateResolverResult`

## 3) Existing automated tests related to the feature slice

### Duplicate detection (logic)

- `jablib/src/test/java/org/jabref/logic/database/DuplicateCheckTest.java`

### Duplicate resolution decisions during import (GUI module, non-UI test)

- `jabgui/src/test/java/org/jabref/gui/externalfiles/ImportHandlerTest.java`
  - Covers keep-right / keep-both / keep-merge decision paths.

### Merge support (GUI module)

- `jabgui/src/test/java/org/jabref/gui/mergeentries/**`
  - Examples:
    - `jabgui/src/test/java/org/jabref/gui/mergeentries/FieldRowViewModelTest.java`
    - `jabgui/src/test/java/org/jabref/gui/mergeentries/ThreeWayMergeViewModelTest.java`
    - `jabgui/src/test/java/org/jabref/gui/mergeentries/DiffHighlightingTest.java`

## 4) Reproducible commands to run the relevant tests

From the repo root:

- Duplicate detection tests:
  - `CI=true ./gradlew :jablib:test --tests org.jabref.logic.database.DuplicateCheckTest`

- Duplicate resolution decision tests:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.externalfiles.ImportHandlerTest`

- Merge-related tests (subset):
  - `CI=true ./gradlew :jabgui:test --tests 'org.jabref.gui.mergeentries.*'`

### Note for Apple Silicon (macOS arm64)

If JavaFX-related tests fail with `No toolkit found` / `no suitable pipeline found`, run Gradle on an **arm64** JDK and clear the JavaFX cache first:

- `./gradlew --stop`
- `rm -rf ~/.openjfx/cache`
- `export JAVA_HOME=$(/usr/libexec/java_home -v 24 -a arm64)` (if you installed an arm64 JDK 24)
- Or use the JDK Gradle provisioned (find the path via `./gradlew -q javaToolchains`, then export that `Location` as `JAVA_HOME`)

Then re-run the commands above.

## 5) Requirement → code/test traceability (from Phase 0)

| Requirement | Where in code | Existing tests located |
|---|---|---|
| **R1 – Duplicate detection** | `DuplicateCheck.isDuplicate(...)` (`jablib/.../DuplicateCheck.java`) and `DuplicateSearch` (`jabgui/.../DuplicateSearch.java`) | `jablib/.../DuplicateCheckTest.java` |
| **R2 – Presentation of duplicates** | “Quality → Find duplicates” wiring in `MainMenu.java`; dialog in `DuplicateResolverDialog.java` | No direct dialog/UI rendering test located; related decision logic is covered in `jabgui/.../ImportHandlerTest.java` |
| **R3 – Safe merge operation** | `MergeEntriesAction` + `MergeTwoEntriesAction` (inserts merged entry and removes originals); merge dialog code under `jabgui/.../mergeentries/**` | `jabgui/.../mergeentries/**` tests + `jabgui/.../ImportHandlerTest.java` (KEEP_MERGE path) |
| **R4 – User control and safety** | Cancel/confirm paths in `MergeEntriesAction` and `DuplicateSearch` | `jabgui/.../ImportHandlerTest.java` includes KEEP_BOTH/KEEP_RIGHT paths; selection precondition is enforced in `MergeEntriesAction` |

## 6) Test strategy in JabRef (overview)

### Structure (where tests live)

- Tests are organized per Gradle module, typically under `*/src/test/java`.
- For this feature slice, the most relevant modules are:
  - `jablib` (core logic, non-GUI): `jablib/src/test/java/...`
  - `jabgui` (GUI + GUI-adjacent logic): `jabgui/src/test/java/...`

### Test tools (from Gradle build files)

- **JUnit 5 (Jupiter + Platform)**: used across modules (e.g., `jablib/build.gradle.kts`, `jabgui/build.gradle.kts`).
- **Mockito**: for mocking collaborators (e.g., in `ImportHandlerTest`).
- **TestFX (JUnit 5 extension)**: for tests requiring JavaFX runtime (e.g., `DiffHighlightingTest` uses `ApplicationExtension`).
- **ArchUnit**: architecture rules (general project strategy; not specific to this slice).

### CI execution (GitHub Actions)

The GitHub Actions workflow `.github/workflows/tests-code.yml` runs tests on **Ubuntu** with:
- **JDK 24 (Corretto)**,
- `CI=true`,
- `xvfb-run --auto-servernum ./gradlew :<module>:check ...` (virtual display for JavaFX tests),
- a module matrix including `jablib`, `jabkit`, `jabsrv`, and `jabgui`.

## 7) Running existing tests (what we ran + results)

### Commands (focused on this slice)

From the repo root:

- `CI=true ./gradlew :jablib:test --tests org.jabref.logic.database.DuplicateCheckTest`
- `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.externalfiles.ImportHandlerTest`
- `CI=true ./gradlew :jabgui:test --tests 'org.jabref.gui.mergeentries.*'`

### What “success” looks like

- Gradle ends with `BUILD SUCCESSFUL`.
- HTML reports are generated, e.g.:
  - `jablib/build/reports/tests/test/index.html`
  - `jabgui/build/reports/tests/test/index.html`
- Evidence screenshot (example): `docs/Assignmet_presentation/phase1-screenshots/03-tests-success.png`

### Notes on macOS reproducibility

- Running `CI=true ./gradlew test` can fail on macOS in `:jabgui:test` due to OS-/environment-specific assumptions (examples we observed: `PushToTeXworksTest`, `ThemeManagerTest`, `LinkedFileViewModelTest`, `KeyBindingsTabModelTest`).
- For reproducible local results on macOS, `CI=true ./gradlew :jablib:test` is typically the most stable suite, and `:jabgui:test` is best run as a targeted subset.

## 8) What the existing tests validate (for this slice)

- **Duplicate detection logic is tested directly (unit-level)**:
  - `DuplicateCheckTest` constructs `BibEntry` objects and asserts when `isDuplicate(...)` should be true/false.
  - It covers cases like type mismatches, different key fields (title/year/journal/pages/edition), and normalization (e.g., umlauts).
- **Duplicate resolution decisions are tested without launching the full UI (component-level)**:
  - `ImportHandlerTest` simulates user decisions by stubbing `getDuplicateDecision(...)`, then asserts database side effects (remove/keep/merge).
- **Merge UI support logic is tested (unit-level + JavaFX runtime where needed)**:
  - `ThreeWayMergeViewModelTest`, `FieldRowViewModelTest`, etc. validate merge view-model behavior.
  - `DiffHighlightingTest` validates diff rendering output and style classes (requires JavaFX runtime via TestFX).

## 9) Test quality observations (focused on duplicates + merge)

- **Good:** `DuplicateCheckTest` uses deterministic inputs, clear oracles (`assertTrue/False`), and exercises many edge cases.
- **Good:** `ImportHandlerTest` validates observable side effects (database entries removed/kept), and avoids UI dependencies by mocking/stubbing.
- **Limitation:** The end-to-end GUI actions are not directly tested (no tests found for `DuplicateSearch` flow, `DuplicateResolverDialog` UI, or `MergeEntriesAction` / `MergeTwoEntriesAction` behavior).
- **Risk:** Some GUI-module tests in JabRef can be OS-sensitive (launching external apps, JavaFX toolkit behavior, timing/file-watch), which affects reproducibility outside CI’s Linux+Xvfb environment.

## 10) Gaps found (inputs for Phase 4 – new tests)

Candidate high-value missing tests for this slice:

- **Merge operation side-effects:** Add tests for `MergeTwoEntriesAction` to verify it inserts the merged entry, removes both originals, and records undo information.
- **Selection precondition:** Add a test for `MergeEntriesAction` when the selection is not exactly two entries (should not merge; should show an info message).
- **Find-duplicates core behavior:** Add tests around `DuplicateSearch` (or its result bookkeeping) to verify which duplicate pairs are produced for a small library and that “keep/merge/break” results are applied correctly.

Next: see `docs/Assignmet_presentation/phase4-new-automated-tests.md` for the tests we implemented from this gap list.

## Screenshot checklist

See `docs/Assignmet_presentation/phase2-screenshots/README.md`.
