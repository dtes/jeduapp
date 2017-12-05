package jeduapp.main.model.study;

import jandcode.dbm.dao.DaoMethod;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import jandcode.utils.UtCnv;
import jandcode.wax.core.model.WaxUpdaterDao;
import mb.core.utils.UtStore;
import org.joda.time.DateTime;

import java.util.Map;

public class AnswersUpdDao extends WaxUpdaterDao {

    @DaoMethod
    public void ins2(long usr, long subChapter, DataStore answers) throws Exception {
        if (answers == null) return;

        // ins answers
        for (DataRecord answer : answers) {
            answer.setValue("usrId", usr);
            ins(answer);
        }

        // calculate result
        int cnt = 0, res;
        String ids = UtStore.join(answers, "variantId", ",");
        DataStore variants = ut.createStore("Variant");

        ut.loadSql(variants, "select * from Variant where id in (${ids})", UtCnv.toMap("ids", ids));
        for (DataRecord variant : variants) {
            if (variant.getValueBoolean("correct")) {
                cnt++;
            }
        }
        res = UtCnv.toInt(cnt * 100 / answers.size());

        //
        Map recData = UtCnv.toMap(
                "usrId", usr,
                "subChapterId", subChapter,
                "dt", DateTime.now(),
                "result", res
        );

        // save result
        ut.insertRec("UsrSubChapterData", recData);

        // update last result
        DataStore store = ut.createStore("UsrSubChapter");
        ut.loadSql(store, "select * from UsrSubChapter \n" +
                "where usrId=:usr \n" +
                "  and subChapterId=:subChapter", UtCnv.toMap(
                //
                "usr", usr,
                "subChapter", subChapter
        ));


        if (store.size() == 0) {
            ut.insertRec("UsrSubChapter", recData);
        } else {
            store.getCurRec().setValues(recData);
            ut.updateRec("UsrSubChapter", store.getCurRec());
        }
    }

}
