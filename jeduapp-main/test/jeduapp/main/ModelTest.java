package jeduapp.main;

import jandcode.dbm.data.DataStore;
import jandcode.dbm.test.DbmTestCase;
import org.junit.Test;

public class ModelTest extends DbmTestCase {

    {
        logSetUp = true;
    }

    @Test
    public void test1() throws Exception {
        DataStore store = (DataStore) dbm.daoinvoke("Subject", "list/load");
        dbm.outTable(store);
    }
}