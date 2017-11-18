package mb.auth.model

import jandcode.dbm.dao.DaoMethod
import jandcode.dbm.data.DataRecord
import jandcode.dbm.data.DataStore
import jandcode.utils.UtString
import jandcode.wax.core.model.WaxListDao

class UsrListDao extends WaxListDao {
    @DaoMethod
    public DataStore load() throws Exception {
        DataStore t = ut.createStore();
        ut.loadSql(t, """
            select ${UtString.join(ut.fieldList(ut.tableName, "passwd"), ",")}
            from ${ut.tableName}
            where id>10
            order by id
        """);
        return t;
    }

    @DaoMethod
    DataRecord loadRec(long id) {
        DataRecord t = ut.createRecord();
        ut.loadSqlRec(t, """
            select ${UtString.join(ut.fieldList(ut.tableName, "passwd"), ",")} from ${ut.tableName} where id=:id
        """, id);
        return t;
    }
}
