> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 2 – Feature Slice Context: Duplicate Detection & Merge

## Goal

Connect the chosen feature slice (“Duplicate detection and merge functionality”) to:
- the **user workflow** in the JabRef GUI,
- the **implementation locations** (modules/classes),
- the **existing automated tests** already in the repository.

This phase does **not** evaluate test quality and does **not** add new tests.

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
