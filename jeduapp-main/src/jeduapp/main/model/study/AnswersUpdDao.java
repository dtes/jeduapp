package jeduapp.main.model.study;

import jandcode.dbm.dao.DaoMethod;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import jandcode.utils.UtCnv;
import jandcode.wax.core.model.WaxUpdaterDao;
import mb.core.utils.UtStore;
import org.joda.time.DateTime;

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
        int cnt = 0;
        String ids = UtStore.join(answers, "variantId", ",");
        DataStore variants = ut.createStore("Variant");

        ut.loadSql(variants, "select * from variant where id in (${ids})", UtCnv.toMap("ids", ids));
        for (DataRecord variant : variants) {
            if (variant.getValueBoolean("correct")) {
                cnt++;
            }
        }
        ut.insertRec("UsrSubChapter", UtCnv.toMap(
                "usrId", usr,
                "subChapterId", subChapter,
                "dt", DateTime.now(),
                "result", UtCnv.toInt(cnt * 100 / answers.size())
        ));
    }

}
