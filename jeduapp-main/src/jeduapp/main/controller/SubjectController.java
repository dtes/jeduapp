package jeduapp.main.controller;

import jandcode.dbm.data.DataBox;
import jandcode.dbm.data.DataStore;
import jandcode.utils.UtCnv;
import mb.core.controller.MbController;

public class SubjectController extends MbController {

    public void show(DataBox params) throws Exception {
        long subjectId = getTokenLong(3);
        DataStore chapters = (DataStore) ut.daoinvoke("Chapter", "list/load");
        renderFrame("subject/show", UtCnv.toMap(
            "chapters", chapters
        ));
    }

}
