# JabRef A2 - Testing Documentation

## 1. Project Overview

JabRef is an open-source reference manager for BibTeX/BibLaTeX with a full GUI. It is actively maintained and includes automated tests, which makes it a practical target for a testing assignment. Data integrity matters because wrong merge decisions can silently corrupt a user’s library.

## 2. Scope and Why This Feature Slice

We scoped the project because JabRef is large and we needed a focused, testable area.

- Scope: duplicate detection + merge entries.
- High impact: wrong merges can cause data loss and user trust issues.
- This slice touches both core logic and GUI behavior, so it is good for test analysis.

## 3. Requirements and Specifications (Inferred)

There is no formal requirements document, so we inferred requirements from behavior, UI, and code. These inferred requirements were used as the test oracle.

- R1: Detect duplicates in a library (logic should identify likely matches).
- R2: Present duplicates clearly (user can compare entries and choose an action).
- R3: Merge safely (merged entry preserves correct data; originals handled correctly).
- R4: Provide user control (confirm, cancel, and undo supported).

## 4. Stakeholders and Risks

Stakeholders:
- Students/researchers who rely on accurate references.
- Maintainers who need stable behavior across releases.

Risks (likelihood/impact):
- False duplicates: medium likelihood, high impact (time loss, incorrect merges).
- Data loss or silent corruption: low to medium likelihood, high impact.
- Privacy exposure from external lookups: low likelihood, medium impact.

## 5. Past/Present/Future Development and Bug Handling

- The repository is actively maintained with CI and an issue tracker (see evidence screenshots).
- Bug handling flows through GitHub issues and CI checks.
- This signals ongoing development and a feedback loop for regression control.

Evidence:
- `evidence/screenshots/phase1/04-ci-pipeline.png`
- `evidence/screenshots/phase1/05-issue-tracker.png`

## 6. Existing Testing Strategy (What already existed)

Existing tests relevant to this slice are mostly unit or component tests:
- Duplicate logic tests in `DuplicateCheckTest`.
- Merge view-model and helper tests under `jabgui/src/test/java/org/jabref/gui/mergeentries/`.
- Decision-path tests in `ImportHandlerTest` (keep/merge/both paths).

Tools and CI:
- JUnit 5, Mockito, and TestFX.
- GitHub Actions runs tests headlessly with Xvfb for JavaFX.

Judgement:
- Strength: logic-level tests are thorough and deterministic.
- Gap: end-to-end GUI flows for duplicate resolver and merge are not covered.

## 7. Testing We Performed

### 7.1 Running existing automated tests

We ran the core module tests and confirmed a clean baseline.

- Commands: see `evidence/commands.md`.
- Evidence of passing tests: `evidence/screenshots/phase1/03-tests-success.png`.

### 7.2 Exploratory testing (GUI)

Exploratory testing was used to validate user workflows and edge cases that are not easily captured by unit tests.

Scenarios tested:
- Exact duplicates and merge (happy path).
- Near duplicates with conflicting metadata.
- Canceling a merge in the duplicate resolver.
- Invalid selection (merge disabled when not exactly two entries).
- Keep-both decision path.

Observation (not claimed as a confirmed bug):
- Canceling the merge dialog does not fully preserve both entries in the duplicate resolver flow. This is risky because it can surprise users and lead to silent data loss.

Evidence:
- `evidence/screenshots/phase3/01-duplicate-resolver-exact.png`
- `evidence/screenshots/phase3/02-merge-dialog-conflicts.png`
- `evidence/screenshots/phase3/03-cancel-merge-behavior.png`
- `evidence/screenshots/phase3/04-invalid-merge-selection.png`
- `evidence/screenshots/phase3/05-keep-both.png`

### 7.3 New/Improved automated tests (test minimization)

We added a minimal set of risk-driven tests to close gaps found in Phase 2.

New tests (copied into evidence):
- `evidence/tests-written/MergeTwoEntriesActionTest.java`
  - Verifies merged entry insertion, removal of originals, and undo creation.
  - Risk addressed: data loss or missing undo support.
- `evidence/tests-written/MergeEntriesActionTest.java`
  - Ensures merge requires exactly two entries.
  - Risk addressed: unsafe or accidental merge operations.
- `evidence/tests-written/DuplicateSearchResultTest.java`
  - Verifies bookkeeping for keep-merge results.
  - Risk addressed: wrong remove/add behavior during duplicate resolution.

macOS stability fixes (modified tests):
- `evidence/tests-written/PushToTeXworksTest.java`
  - OS-specific command line format for external app launch.
- `evidence/tests-written/KeyBindingsTabModelTest.java`
  - OS-specific shortcut modifier mapping (Command vs Control).

Why automation matters here:
- These tests guard against regressions in safety-critical workflows.
- They are fast and deterministic compared to manual GUI testing.

Evidence of test runs:
- `evidence/screenshots/phase4/01-new-tests-pass.png`
- `evidence/screenshots/phase4/02-macos-fix-tests-pass.png`

## 8. Test Quality Assessment (Higher-grade requirement)

Strengths:
- Core logic for duplicates is well tested.
- Decision-path testing exists without needing full GUI automation.

Weaknesses:
- End-to-end GUI flows (duplicate resolver/merge dialog) are not covered.
- Coverage is moderate overall; the Codecov badge shows ~40% coverage.

Evidence:
- `evidence/screenshots/phase1/06-codecov-coverage.svg`

Confidence gained:
- We are confident in the merge side effects and selection safety.
- We have less confidence in complete GUI workflows under all environments.

Suggested improvements:
- Add GUI-level end-to-end tests for duplicate resolver and merge dialog.
- Add performance tests for large libraries (duplicate search scaling).

## 9. Non-functional Evaluation

- Reliability: merge and duplicate operations are undoable, reducing data loss risk.
- Performance: duplicate search is O(n^2), which can be slow for large libraries.
- Privacy: external lookups and telemetry are documented in the project’s privacy policy.
- Accessibility: the GUI supports keyboard shortcuts, but no full accessibility audit was performed.

Evidence:
- `evidence/screenshots/phase5/01-privacy-policy.png`

## 10. Limitations (Required for higher-grade)

- No full performance benchmarks were executed.
- No security audit or penetration testing was performed.
- Manual cross-platform testing was limited (local macOS + CI environment only).

## 11. Traceability (Requirements to Evidence)

A simple traceability table is provided here:
- `evidence/traceability.md`

## 12. Conclusion

We focused on a high-risk feature slice, confirmed baseline behavior, and added targeted tests to improve confidence.

Testing is about confidence, not coverage.
