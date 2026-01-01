> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 5 – Non-Functional Evaluation (JabRef / Duplicate Detection & Merge)

## Scope (what this phase covers)

This non-functional evaluation is focused on our chosen feature slice:

- **Quality → Find duplicates**
- **Quality → Merge entries**

We also include a small amount of **project-wide** non-functional context where it affects user trust (privacy/security and external integrations).

## Method (how we evaluated)

- **Documentation review:** JabRef docs (user-facing) and repository docs (e.g., privacy policy).
- **Code inspection (risk-based):** we inspected where duplicates and merge are implemented to understand reliability/performance characteristics (e.g., undo behavior, algorithmic complexity, OS-specific behavior).
- **Local execution context:** informed by Phase 1/2/4 work (build/run/tests on macOS).

This is not a full performance benchmark or security audit; see limitations below.

## Stakeholders and risks (high level)

- **Primary users:** students/researchers managing large bibliographies.
- **Key risk:** incorrect duplicate detection or accidental merges can cause **data loss** or **loss of trust**.
- **Secondary risk:** privacy concerns due to network features (metadata fetching, update checks, external services).

## Reliability & data integrity

- **User confirmation and cancel paths:**
  - Duplicate resolution is interactive (user chooses keep/merge/both/break).
  - Merge requires selecting **exactly two** entries and confirming in a dialog; canceling should not modify the database.
- **Undo support (important reliability mechanism):**
  - Both duplicate removal and merge operations are wrapped in undoable edits (compound undo), enabling recovery from mistakes.
  - This reduces the impact of false positives in duplicate detection.
- **Failure modes to be aware of:**
  - Duplicate detection uses heuristics; false positives/negatives are possible, especially with incomplete metadata.
  - Large batches of duplicates can cause user fatigue; the “apply to all” option helps, but also increases risk if the user selects the wrong option.

## Usability (discoverability and clarity)

- **Discoverability:** Both actions are placed under **Quality**, which matches user expectations (cleanup/quality tools).
- **Dialog clarity:** The duplicate resolver/merge dialog provides side-by-side comparison and explicit choices (keep left/right/both/merge).
- **Error prevention:** enforcing “exactly two entries” for merge prevents accidental multi-merge behavior.

## Performance & scalability

- **Duplicate search complexity:** The implementation checks entry pairs in nested loops (roughly *n·(n−1)/2* comparisons), i.e., **O(n²)** behavior for a library with *n* entries.
  - For large libraries, this can become slow.
  - The search is executed in background work, which helps keep the UI responsive, but runtime can still be significant.
- **Practical implication for users:** On big libraries, users may need to run duplicate search selectively (e.g., after imports) rather than repeatedly on the entire dataset.

## Security & privacy (project-wide considerations)

- **Local data operations:** Duplicate detection and merge are local operations on the bibliography database.
- **External communication:** JabRef has optional features that communicate with online services (updates, metadata lookups, journal info, etc.). The project documents these behaviors in `PRIVACY.md`.
  - Even when the app does not “collect personal info”, users’ IP address and query terms may be visible to third-party services during lookups.
- **External application launching:** JabRef can “push to applications” (e.g., TeX editors). On macOS, it launches apps using the system `open -a ... --args ...` mechanism; on other OSes, it uses direct commands.
  - Risk is mostly local (user-configured command paths); no direct remote exposure, but it’s still part of the trust surface.

## Accessibility (brief)

We did not perform a full accessibility audit. However, JabRef exposes keyboard shortcuts and supports keyboard-driven workflows, which can improve accessibility for experienced users.

## Limitations (what we did NOT test and why)

- No formal **load/performance benchmarking** (time, memory) for very large libraries.
- No **security testing** (threat modeling, penetration testing, dependency audit).
- No cross-platform manual verification on Windows/Linux (our local environment is macOS; CI runs most tests on Linux).
- We did not attempt long-run reliability (soak tests) or recovery testing (crash mid-merge, forced shutdown).

## Screenshot checklist

See `docs/Assignmet_presentation/phase5-screenshots/README.md`.

