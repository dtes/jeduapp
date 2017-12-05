package jeduapp.main.model.study;

import jandcode.dbm.dao.DaoMethod;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import jandcode.utils.UtCnv;
import jandcode.wax.core.model.WaxListDao;

public class SubChapterExtListDao extends WaxListDao {

    @DaoMethod
    public DataRecord loadRec(long usr, long subChapter) throws Exception {
        DataStore subChapters = ut.createStore("SubChapterExt");

        ut.loadSql(subChapters, "select sc.*, usc.result,\n" +
                "  (case when usc.id is null then 0 else 1 end) train \n" +
                "from SubChapter sc \n" +
                "left join UsrSubChapter usc on usc.subChapterId=sc.id and usc.usrId=:usr\n" +
                "where sc.id=:subChapter", UtCnv.toMap(
                //
                "usr", usr,
                "subChapter", subChapter
        ));

        return subChapters.getCurRec();
    }

    @DaoMethod
    public DataStore load(long usr, long chapter) throws Exception {
        DataStore subChapters = ut.createStore("SubChapterExt");

        ut.loadSql(subChapters, "select sc.*, usc.result,\n" +
                "  (case when usc.id is null then 0 else 1 end) train \n" +
                "from SubChapter sc \n" +
                "left join UsrSubChapter usc on usc.subChapterId=sc.id and usc.usrId=:usr\n" +
                "where sc.chapterId=:chapter", UtCnv.toMap(
                //
                "usr", usr,
                "chapter", chapter
        ));

        return subChapters;
    }

}
