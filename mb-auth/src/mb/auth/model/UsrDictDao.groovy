package mb.auth.model

import jandcode.dbm.dao.DaoMethod
import jandcode.dbm.data.DataStore
import jandcode.dbm.dict.ILoadDict
import jandcode.utils.CollectionBlockIterator
import jandcode.utils.UtString
import jandcode.wax.core.model.WaxDao

class UsrDictDao extends WaxDao implements ILoadDict {
    @DaoMethod
    void loadDict(jandcode.dbm.dict.Dict dict) {
        Set ids = dict.getResolveIds()
        DataStore res = dict.getData()
        //
        CollectionBlockIterator z = new CollectionBlockIterator(ids, 200);
        for (List itms : z) {
            String itmsS = UtString.join(itms, ",");
            String sql = "select * from ${ut.tableName} where id in (${itmsS})";
            ut.loadSql(res, sql);
        }
    }
}
