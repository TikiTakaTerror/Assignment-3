package org.jabref.gui.duplicationFinder;

import java.util.List;

import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.types.StandardEntryType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateSearchResultTest {

    @Test
    void replaceWithMergeMarksBothOriginalEntriesForRemovalAndAddsMergedEntry() {
        DuplicateSearch.DuplicateSearchResult result = new DuplicateSearch.DuplicateSearchResult();

        BibEntry first = new BibEntry(StandardEntryType.Article).withCitationKey("First");
        BibEntry second = new BibEntry(StandardEntryType.Article).withCitationKey("Second");
        BibEntry merged = new BibEntry(StandardEntryType.Article).withCitationKey("Merged");

        result.replace(first, second, merged);

        assertTrue(result.isToRemove(first));
        assertTrue(result.isToRemove(second));
        assertEquals(2, result.getToRemove().size());
        assertEquals(List.of(merged), result.getToAdd());
    }
}
