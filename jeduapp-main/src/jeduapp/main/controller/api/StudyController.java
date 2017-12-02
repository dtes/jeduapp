package jeduapp.main.controller.api;

import jandcode.dbm.data.DataBox;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import jandcode.utils.UtCnv;
import mb.core.controller.MbController;
import mb.core.utils.UtStore;

public class StudyController extends MbController {

    long usr = 1000;

    public void getSubject(DataBox params) throws Exception {
        long id = params.getValueLong("id");
        DataRecord subject = ut.getDictRec(id, "Subject");

        renderJson(UtCnv.toMap(
                "subject", subject
        ));
    }

    public void getChapters(DataBox params) throws Exception {
        long subjectId = params.getValueLong("subjectId");
        DataStore chapters = ut.getDict("Chapter").getData();
        chapters = UtStore.filter(chapters, "subjectId", subjectId, app);

        renderJson(UtCnv.toMap(
                "chapters", chapters
        ));
    }

    public void getSubChapters(DataBox params) throws Exception {
        long chapterId = params.getValueLong("chapterId");
        DataStore subChapters = ut.getDict("SubChapter").getData();
        subChapters = UtStore.filter(subChapters, "chapterId", chapterId, app);

        renderJson(UtCnv.toMap(
                "subChapters", subChapters
        ));
    }

    public void getQuestions(DataBox params) throws Exception {
        long subChapterId = params.getValueLong("subChapterId");
        DataBox box = (DataBox) ut.daoinvoke("Question", "list/loadBySubChapter", subChapterId);

        renderJson(UtCnv.toMap(
                "box", box
        ));
    }

    public void finishTest(DataBox params) throws Exception {
        long subChapterId = params.getValueLong("subChapter");
        DataStore answers = (DataStore) params.get("answers");
        ut.daoinvoke("Answer", "updater/ins2", usr, subChapterId, answers);

        renderSuccess();
    }

}
