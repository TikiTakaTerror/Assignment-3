> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](./AGENTS.md)
> • [CONTRIBUTING.md](./CONTRIBUTING.md)

# Commands Used

## Build and run

Build artifacts without tests:
```
./gradlew assemble
```

Run the JabRef GUI:
```
./gradlew run
```

## Existing tests (baseline)

Run core module tests with CI settings:
```
CI=true ./gradlew :jablib:test
```

Run duplicate logic tests only:
```
CI=true ./gradlew :jablib:test --tests org.jabref.logic.database.DuplicateCheckTest
```

## New and modified tests (targeted)

Merge side effects test:
```
CI=true ./gradlew :jabgui:test --tests org.jabref.gui.mergeentries.threewaymerge.MergeTwoEntriesActionTest
```

Selection precondition test:
```
CI=true ./gradlew :jabgui:test --tests org.jabref.gui.mergeentries.threewaymerge.MergeEntriesActionTest
```

Duplicate bookkeeping test:
```
CI=true ./gradlew :jabgui:test --tests org.jabref.gui.duplicationFinder.DuplicateSearchResultTest
```

macOS fixes:
```
CI=true ./gradlew :jabgui:test --tests org.jabref.gui.push.PushToTeXworksTest
CI=true ./gradlew :jabgui:test --tests org.jabref.gui.keyboard.KeyBindingsTabModelTest
```

## Coverage

Generate a JaCoCo HTML report (full run):
```
./gradlew jacocoTestReport
```

Generate a JaCoCo report after a targeted test run (jablib only):
```
CI=true ./gradlew :jablib:jacocoTestReport -x test -x fetcherTest -x databaseTest
```
