package jeduapp.main.model.study;

import jandcode.dbm.dao.DaoMethod;
import jandcode.dbm.data.DataStore;
import jandcode.wax.core.model.WaxListDao;

public class ChapterListDao extends WaxListDao {

    @DaoMethod
    public DataStore loadSubChapters(long chapterId) throws Exception {
        DataStore ds = ut.createStore("SubChapter");
        ut.loadSql(ds, "select * from SubChapter where chapterId=:id", chapterId);
        return ds;
    }

}
