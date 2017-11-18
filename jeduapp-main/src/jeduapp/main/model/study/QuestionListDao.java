package jeduapp.main.model.study;

import jandcode.dbm.dao.DaoMethod;
import jandcode.dbm.data.DataBox;
import jandcode.dbm.data.DataStore;
import jandcode.dbm.data.UtData;
import jandcode.utils.UtCnv;
import jandcode.utils.UtString;
import jandcode.wax.core.model.WaxListDao;

public class QuestionListDao extends WaxListDao {

    @DaoMethod
    public DataBox loadBySubChapter(long subChapter) throws Exception {
        DataBox box = new DataBox();
        DataStore questions = ut.createStore("Question");
        DataStore variants = ut.createStore("Variant");

        ut.loadSql(questions, "select * from question where subChapterId=:id", subChapter);

        if (questions.size() > 0) {
            String ids = UtString.join(UtData.uniqueValues(questions, "id"), ",");
            ut.loadSql(variants, "select * from variant where questionId in (${ids})", UtCnv.toMap("ids", ids));
        }

        box.put("questions", questions);
        box.put("variants", variants);

        return box;
    }

}
