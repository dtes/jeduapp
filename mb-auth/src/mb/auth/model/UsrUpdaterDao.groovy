package mb.auth.model

import jandcode.dbm.dao.DaoMethod
import jandcode.dbm.data.DataRecord
import jandcode.utils.UtString
import jandcode.utils.error.XError
import jandcode.wax.core.model.WaxUpdaterDao

class UsrUpdaterDao extends WaxUpdaterDao {
    protected void validatePassword(DataRecord t) {
        String p1 = t.getValueString("passwd");
        String p2 = t.getValueString("passwd2");
        //
        if (UtString.empty(p1)) {
            ut.getErrors().addError("Пароль не введен", "passwd");
            return;
        }
        if (p1 != p2) {
            ut.getErrors().addError("Пароли не совпадают", "passwd");
        }
    }

    @DaoMethod
    public long ins(DataRecord data) throws Exception {
        DataRecord t = ut.createRecord("Usr.edit", data);
        //
        ut.validateRecord(t, "ins");
        validatePassword(t)
        ut.checkErrors();
        //
        t.setValue("passwd", UtString.md5Str(data.getValueString("passwd")));
        long id = ut.insertRec(ut.getTableName(), t);
        return id
    }

    /**
     * Обновление всего, кроме пароля
     */
    @DaoMethod
    public void upd(DataRecord data) throws Exception {
        DataRecord t = ut.createRecord("Usr.edit", data);
        //
        ut.validateRecord(t, "upd");
        ut.checkErrors();
        //
        ut.updateRec(ut.getTableName(), t, null, ['passwd', 'passwd2']);
    }

    @DaoMethod
    public void del(long id) throws Exception {
        //
        if (id < 11) throw new XError("Системного пользователя удалить нельзя");
        //
        ut.validateDelRef(ut.getTableName(), id);
        ut.checkErrors();
        //
        ut.deleteRec(ut.getTableName(), id);
    }

    /**
     * Обновление пароля (из админки, не требуется старый пароль)
     */
    @DaoMethod
    public void updPasswd(DataRecord data) throws Exception {
        validatePassword(data);
        ut.checkErrors();
        //
        ut.updateRec(ut.getTableName(), [id: data.getValueLong("id"), passwd: UtString.md5Str(data.getValueString("passwd"))]);
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
