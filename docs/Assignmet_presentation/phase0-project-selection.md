> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Phase 0 – Project Selection & Scope Definition

## Project Overview
**Project name:** JabRef  
**Repository:** https://github.com/JabRef/jabref  
**Project type:** Open-source desktop application (GUI)  
**Domain:** Reference and citation management

JabRef is an open-source reference management tool primarily aimed at researchers, students, and academics. The application allows users to collect, organize, search, and maintain bibliographic references, mainly using BibTeX and BibLaTeX formats. It provides a graphical user interface and supports common workflows such as importing references, managing duplicates, attaching documents, and fetching metadata from external sources.

The project is actively maintained, has a large codebase, and is widely used, which makes it suitable for systematic testing and evaluation.

---

## Suitability for the Assignment
JabRef fulfills all mandatory requirements for the A2 assignment:

- It is a fully functional application with a graphical user interface, enabling both automated and manual testing.
- The project is actively maintained and has a long development history.
- The functionality and expected behavior of the system can be clearly understood through the README, documentation, and the user interface itself.
- The repository includes automated tests, which can be executed and analyzed as part of this assignment.
- The project has clear user-oriented features that allow exploratory testing and requirement-based evaluation.

These characteristics make JabRef a suitable and realistic candidate for studying existing testing strategies and performing additional testing activities.

---

## Chosen Feature Slice
**Feature slice:** Duplicate detection and merge functionality

Rather than testing the entire application, this assignment focuses on a specific and manageable feature slice: the detection and merging of duplicate bibliographic entries.

This feature is central to JabRef’s purpose, as reference libraries often contain duplicates introduced through imports, manual entry, or metadata fetching. The feature also provides clear system behavior, user interaction through the GUI, and underlying logic that can be validated through automated unit tests.

---

## Motivation for Choosing This Feature Slice
The duplicate detection and merge functionality was chosen for the following reasons:

- The scope is limited and well-defined, making it feasible to analyze deeply within the time constraints of the assignment.
- The feature is critical for data quality and user trust, which makes correctness especially important.
- It enables both manual exploratory testing (through the graphical interface) and automated testing (through existing test cases).
- The feature naturally exposes edge cases, such as partially matching entries or conflicting metadata, which are valuable from a testing perspective.

This makes the feature slice well-suited for evaluating test coverage, test quality, and identifying potential gaps.

---

## Initial Requirements (Inferred)
Since JabRef does not provide a formal requirements specification, the following requirements are inferred from the project’s documentation and observable system behavior:

- **R1 – Duplicate detection:**  
  The system should be able to detect potential duplicate entries within a reference library based on bibliographic information.

- **R2 – Presentation of duplicates:**  
  Detected duplicates should be presented to the user in a clear manner, allowing inspection and comparison of entries.

- **R3 – Safe merge operation:**  
  The user should be able to merge duplicate entries into a single reference without losing important bibliographic information.

- **R4 – User control and safety:**  
  Duplicate entries should not be merged automatically without user confirmation, and the user should be able to cancel the operation without affecting the library.

These requirements will be used later to analyze existing tests, guide exploratory testing, and identify potential missing test cases.

---

## Stakeholders and Risks (for testing focus)

**Stakeholders (examples):**

- researchers/students using JabRef daily to manage references
- maintainers/contributors ensuring stability across platforms
- users relying on correct exports (BibTeX/BibLaTeX) for papers/theses

**Key risks (examples):**

- incorrect duplicate detection (false positives/negatives) reduces trust and increases manual cleanup
- wrong merge decisions can cause **data loss** or “silently” change citation metadata
- privacy concerns when external lookups are enabled (network requests, metadata fetching)

---

## Expected Evidence and Artifacts (Later Phases)
In later phases of the assignment, the following evidence related to this feature slice will be collected:

- Screenshots of the duplicate detection and merge workflow in the GUI.
- Results from running existing automated tests related to duplicate handling.
- Mapping between inferred requirements and existing tests.
- Observations from exploratory testing and identified risks or issues.

---

**Phase 0 status:** Completed  
**Next phase:** Phase 1 – Project setup, execution, and existing test execution
