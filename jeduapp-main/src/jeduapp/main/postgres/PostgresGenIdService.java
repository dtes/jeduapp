package jeduapp.main.postgres;

import jandcode.dbm.data.DataIndex;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import jandcode.dbm.data.UtData;
import jandcode.dbm.db.Db;
import jandcode.dbm.db.DbQuery;
import jandcode.dbm.db.GenIdService;
import jandcode.utils.UtCnv;
import jandcode.utils.UtString;
import jandcode.utils.error.XErrorWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostgresGenIdService extends GenIdService {
    protected static Log log = LogFactory.getLog(PostgresGenIdService.class);

    private long recoverIncrement = 1000;

    private String seqPrefix = "SEQ_";

    //////

    public long getNextId(String name, long count) {
        Db db = getDbSource().getDb();
        long res = 0;
        try {
            name = name.toUpperCase();
            DbQuery qR;
            if (count == 1) {
                qR = db.openSql("SELECT nextval('${seq_name}') new_id",
                        UtCnv.toMap("seq_name", seqPrefix + name.toUpperCase()));
            } else {
                // todo: нафиг это нужно? так и не понял
                qR = db.openSql("select seq_pkg.get_next_id(:{seq_name},:{count}) new_id from dual",
                        UtCnv.toMap("seq_name", seqPrefix + name.toUpperCase(), "count", count));
            }

            try {
                res = qR.getValueLong("new_id");
            } finally {
                qR.close();
            }
        } catch (Exception e) {
            throw new XErrorWrap(e);
        }
        return res;
    }

    public void setNextId(String name, long val) {
        try {
            getDbSource().getDb().execSql("SELECT setval(:{seq}, :{val})",
                    UtCnv.toMap("seq", seqPrefix + name.toUpperCase(), "val", val));
        } catch (Exception e) {
            throw new XErrorWrap(e);
        }
    }

    public void recoverGenId() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("Восстановление генераторов");
        }

        DataStore curTabs = getDbSource().getDb().loadSql("SELECT upper(table_name) as name " +
                "FROM information_schema.tables " +
                "where table_schema=:{username}", UtCnv.toMap(
                //
                "username", getDbSource().getUsername()
        ));
        DataIndex idxTabs = UtData.createIndex(curTabs, "name");

        //
        DataStore curGens = getDbSource().getDb().loadSql("SELECT upper(sequence_name) as name " +
                "FROM information_schema.sequences " +
                "where sequence_schema=:{username}", UtCnv.toMap(
                //
                "username", getDbSource().getUsername()
        ));

        for (DataRecord rec : curGens) {
            // имя генератора
            String genName = rec.getValueString("name").toUpperCase();
            String tabName = UtString.removePrefix(genName, seqPrefix);
            if (tabName == null) {
                // это не наш генератор, у него нет префикса
                continue;
            }
            DataRecord tb = idxTabs.get(tabName);
            if (tb == null) {
                // и это не наш генератор, нет такой таблицы
                continue;
            }
            //
            try {
                // todo: Нужно сделать как в OracleGenIdService
                DataStore maxId = getDbSource().getDb().loadSql("select greatest(max(id), 0) max_id from " + tabName);
                getDbSource().getDb().execSql("SELECT setval(:{seq},:{rec_inc})\n", UtCnv.toMap(
                        "seq", genName,
                        "rec_inc", Math.max(recoverIncrement, maxId.getCurRec().getValueLong("max_id"))
                ));
                if (log.isInfoEnabled()) {
                    log.info("Генератор: " + genName + " обновлен");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (log.isWarnEnabled()) {
                    log.warn("Ошибка при обновлении генератора: " + genName, e);
                }
            }
        }

        if (log.isInfoEnabled()) {
            log.info("Восстановление генераторов закончено");
        }
    }

}
