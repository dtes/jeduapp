package jeduapp.main.model.study;

import jandcode.dbm.data.DataStore;
import jandcode.dbm.test.DbmTestCase;
import org.junit.Test;

public class StudyTest extends DbmTestCase {

    @Test
    public void testLoadSubChaptersByChapter() throws Exception {
        DataStore ds = (DataStore) dbm.daoinvoke("Chapter", "list/loadSubChapters", 1);
        dbm.outTable(ds);
    }



}
