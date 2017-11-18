package jeduapp.main.postgres;

import jandcode.dbm.data.DataStore;
import jandcode.dbm.db.jdbc.JdbcMetaDataService;

public class PostgresMetadataService extends JdbcMetaDataService {
    @Override
    protected String getCurrentSchema() throws Exception {
        DataStore t = getDbSource().getDb().loadSql("select current_schema");
        return t.getCurRec().getValueString("current_schema");
    }
}
