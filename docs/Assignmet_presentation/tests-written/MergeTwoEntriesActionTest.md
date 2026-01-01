> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](../AGENTS.md)
> • [CONTRIBUTING.md](../CONTRIBUTING.md)

# `MergeTwoEntriesActionTest`

Original file: `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeTwoEntriesActionTest.java`

```java
package org.jabref.gui.mergeentries.threewaymerge;

import java.util.Optional;

import javax.swing.undo.UndoManager;

import org.jabref.gui.StateManager;
import org.jabref.gui.undo.NamedCompoundEdit;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.types.StandardEntryType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MergeTwoEntriesActionTest {

    @Test
    void executeInsertsMergedEntryRemovesOriginalsAndCreatesUndoEdit() {
        BibDatabase database = new BibDatabase();
        BibDatabaseContext databaseContext = new BibDatabaseContext(database);

        BibEntry leftEntry = new BibEntry(StandardEntryType.Article).withCitationKey("Left");
        BibEntry rightEntry = new BibEntry(StandardEntryType.Article).withCitationKey("Right");
        BibEntry mergedEntry = new BibEntry(StandardEntryType.Article).withCitationKey("Merged");

        database.insertEntry(leftEntry);
        database.insertEntry(rightEntry);

        EntriesMergeResult mergeResult = new EntriesMergeResult(leftEntry, rightEntry, leftEntry, rightEntry, mergedEntry);

        StateManager stateManager = mock(StateManager.class);
        when(stateManager.getActiveDatabase()).thenReturn(Optional.of(databaseContext));

        UndoManager undoManager = mock(UndoManager.class);

        MergeTwoEntriesAction action = new MergeTwoEntriesAction(mergeResult, stateManager, undoManager);
        action.execute();

        assertTrue(database.getEntries().contains(mergedEntry));
        assertFalse(database.getEntries().contains(leftEntry));
        assertFalse(database.getEntries().contains(rightEntry));
        verify(undoManager).addEdit(any(NamedCompoundEdit.class));
    }

    @Test
    void executeDoesNothingWhenNoDatabaseIsActive() {
        StateManager stateManager = mock(StateManager.class);
        when(stateManager.getActiveDatabase()).thenReturn(Optional.empty());

        UndoManager undoManager = mock(UndoManager.class);

        EntriesMergeResult mergeResult = new EntriesMergeResult(
                new BibEntry(StandardEntryType.Article),
                new BibEntry(StandardEntryType.Article),
                new BibEntry(StandardEntryType.Article),
                new BibEntry(StandardEntryType.Article),
                new BibEntry(StandardEntryType.Article));

        MergeTwoEntriesAction action = new MergeTwoEntriesAction(mergeResult, stateManager, undoManager);
        action.execute();

        verifyNoInteractions(undoManager);
    }
}
```

