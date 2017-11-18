package jeduapp.main.postgres;

import jandcode.dbm.db.DbDriver;

public class PostgresDbDriver extends DbDriver {

    public PostgresDbDriver() {
        setDbType("postgresql");
    }
}
