package jeduapp.main.postgres;

import jandcode.dbm.data.DataStore;
import jandcode.dbm.db.Db;
import jandcode.dbm.db.jdbc.JdbcDbManagerService;

public class PostgresDbManagerService extends JdbcDbManagerService {

    public boolean existDatabase() throws Exception {
        boolean res = false;
        Db dbsys = getSystemDb();
        dbsys.connect();
        try {
            DataStore a = dbsys.loadSql("SELECT * FROM information_schema.schemata WHERE schema_name=:id",
                    getDbSource().getUsername());
            res = a.size() == 1;
        } finally {
            dbsys.disconnect();
        }
        return res;
    }

    public void createDatabase() throws Exception {
        Db dbsys = getSystemDb();
        dbsys.connect();
        try {
            String username = getDbSource().getUsername();
            dbsys.execSqlNative("CREATE USER " + username + " WITH PASSWORD '" + getDbSource().getPassword() + "'");
            dbsys.execSqlNative("CREATE SCHEMA " + username);
            dbsys.execSqlNative("GRANT ALL ON SCHEMA " + username + " TO " + username);
            dbsys.execSqlNative("ALTER USER " + username + " SET search_path TO " + username);
        } finally {
            dbsys.disconnect();
        }
    }

    public void dropDatabase() throws Exception {
        Db dbsys = getSystemDb();
        dbsys.connect();
        try {
            dbsys.execSqlNative("DROP SCHEMA " + getDbSource().getUsername() + " CASCADE");
            dbsys.execSqlNative("DROP USER " + getDbSource().getUsername());
        } finally {
            dbsys.disconnect();
        }
    }

}
