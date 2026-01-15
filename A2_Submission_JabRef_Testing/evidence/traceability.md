# Traceability Matrix

| Requirement | Evidence (tests / exploratory / screenshots) | Notes |
| --- | --- | --- |
| R1 - Detect duplicates | `evidence/tests-written/DuplicateSearchResultTest.java`, `evidence/screenshots/phase3/01-duplicate-resolver-exact.png` | Confirms duplicate handling and bookkeeping in the workflow. |
| R2 - Present duplicates clearly | `evidence/screenshots/phase3/01-duplicate-resolver-exact.png`, `evidence/screenshots/phase3/02-merge-dialog-conflicts.png` | UI shows side-by-side comparison and conflict highlights. |
| R3 - Merge safely | `evidence/tests-written/MergeTwoEntriesActionTest.java`, `evidence/screenshots/phase4/01-new-tests-pass.png` | Verifies merged entry insertion/removal and undo support. |
| R4 - User control and safety | `evidence/tests-written/MergeEntriesActionTest.java`, `evidence/screenshots/phase3/04-invalid-merge-selection.png`, `evidence/screenshots/phase3/03-cancel-merge-behavior.png` | Merge blocked unless selection is valid; cancel behavior observed. |
