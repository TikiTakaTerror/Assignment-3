> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Tests Written / Modified (Presentation Copy)

This folder is a **presentation-friendly copy** of the tests we added/modified for the assignment, so you can show them without navigating deep into the repository.

> Source of truth is always the actual test files under `jabgui/src/test/java/...`.

## New tests (added by us)

- `DuplicateSearchResultTest.md` (original: `jabgui/src/test/java/org/jabref/gui/duplicationFinder/DuplicateSearchResultTest.java`)
- `MergeEntriesActionTest.md` (original: `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeEntriesActionTest.java`)
- `MergeTwoEntriesActionTest.md` (original: `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeTwoEntriesActionTest.java`)

## Existing tests (modified by us for macOS)

- `PushToTeXworksTest.md` (original: `jabgui/src/test/java/org/jabref/gui/push/PushToTeXworksTest.java`)
- `KeyBindingsTabModelTest.md` (original: `jabgui/src/test/java/org/jabref/gui/keyboard/KeyBindingsTabModelTest.java`)

## Run commands (same as Phase 4)

- New tests only:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.mergeentries.threewaymerge.MergeTwoEntriesActionTest --tests org.jabref.gui.mergeentries.threewaymerge.MergeEntriesActionTest --tests org.jabref.gui.duplicationFinder.DuplicateSearchResultTest`
- macOS-related fixes:
  - `CI=true ./gradlew :jabgui:test --tests org.jabref.gui.push.PushToTeXworksTest --tests org.jabref.gui.keyboard.KeyBindingsTabModelTest`

