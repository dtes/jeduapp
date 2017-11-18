package mb.auth.model

import jandcode.auth.IUserInfo
import jandcode.dbm.dao.DaoMethod
import jandcode.dbm.data.DataRecord
import jandcode.utils.UtString
import jandcode.utils.error.XError
import jandcode.wax.core.model.WaxDao
import mb.auth.impl.UserInfoImpl

class MbUsrAuthDao extends WaxDao {
    /**
     * login
     * @param username имя пользователя
     * @param passwd пароль
     * @return информация о пользователе или ошибка
     */
    @DaoMethod
    public IUserInfo login(String login, String passwd) throws Exception {
        login = login.toLowerCase()
        passwd = UtString.md5Str(passwd)
        def store = ut.createStore()
        ut.loadSql(store, getLoadSql(login, passwd), [login: login, passwd: passwd])
        if (store.size() == 0) {
            throw new XError("Имя пользователя или пароль неправильные")
        }
        //
        def rec = store.getCurRec()
        // дополнительные проверки
        onValidate(rec)
        //
        return loadUserInfoForRec(rec)
    }

    /**
     * Создать и загрузить IUserInfo по записи пользователя
     * @param rec
     * @return
     */
    @DaoMethod
    public IUserInfo loadUserInfoForRec(DataRecord rec) throws Exception {
        //
        UserInfoImpl ui = new UserInfoImpl()
        ui.setId(rec.getValueLong("id"))
        ui.setUsername(rec.getValueString("username"))
        ui.setEmail(rec.getValueString("email"))
        ui.setFullname(rec.getValueString("fullname"))
        ui.setGuest(false)
        ui.setLocked(rec.getValueBoolean("locked"))
        // дополнительные атрибуты
        // значения из всех остальных полей, которые не помечены как стандартные
        for (f in rec.domain.fields) {
            if (!f.rt.getValueBoolean("auth.stdfield")) {
                ui.attrs[f.name] = rec.isValueNull(f) ? null : rec.getValue(f)
            }
        }
        return ui
    }

    @DaoMethod
    public IUserInfo getUsrInfo(long userId) {
        def r = ut.loadRec("Usr", userId)
        return loadUserInfoForRec(r)
    }

    protected void onValidate(DataRecord rec) throws Exception {
        if (rec.getValueBoolean("locked")) {
            throw new XError("Пользователь заблокирован")
        }
    }

    protected String getLoadSql(String login, String passwd) throws Exception {
        String loginField = getApp().getRt().getChild("app/conf/loginField").getValueString("value")
        return "select * from ${ut.tableName} where ${loginField}=:login and passwd=:passwd"
    }

}
