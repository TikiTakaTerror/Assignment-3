> [!IMPORTANT]
> This project does not accept fully AI-generated pull requests. AI tools may be used assistively only. You must understand and take responsibility for every change you submit.
>
> Read and follow:
> • [AGENTS.md](../AGENTS.md)
> • [CONTRIBUTING.md](../CONTRIBUTING.md)

# `MergeEntriesActionTest`

Original file: `jabgui/src/test/java/org/jabref/gui/mergeentries/threewaymerge/MergeEntriesActionTest.java`

```java
package org.jabref.gui.mergeentries.threewaymerge;

import java.util.Optional;

import javax.swing.undo.UndoManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.jabref.gui.DialogService;
import org.jabref.gui.StateManager;
import org.jabref.gui.preferences.GuiPreferences;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.types.StandardEntryType;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MergeEntriesActionTest {

    @Test
    void executeShowsInfoDialogWhenSelectionIsNotExactlyTwoEntries() {
        DialogService dialogService = mock(DialogService.class);
        StateManager stateManager = mock(StateManager.class);
        UndoManager undoManager = mock(UndoManager.class);
        GuiPreferences preferences = mock(GuiPreferences.class);

        ObservableList<BibEntry> selectedEntries = FXCollections.observableArrayList(
                new BibEntry(StandardEntryType.Article)
        );

        when(stateManager.getActiveDatabase()).thenReturn(Optional.of(new BibDatabaseContext()));
        when(stateManager.getSelectedEntries()).thenReturn(selectedEntries);

        MergeEntriesAction action = new MergeEntriesAction(dialogService, stateManager, undoManager, preferences);
        action.execute();

        verify(dialogService).showInformationDialogAndWait(
                Localization.lang("Merge entries"),
                Localization.lang("You have to choose exactly two entries to merge."));
        verifyNoInteractions(undoManager);
    }
}
```

