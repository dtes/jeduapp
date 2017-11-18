package mb.auth.filter;

import jandcode.auth.AuthService;
import jandcode.auth.IUserInfo;
import jandcode.dbm.ModelService;
import jandcode.utils.UtString;
import jandcode.web.WebFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthFilter extends WebFilter {
    protected static Log log = LogFactory.getLog(AuthFilter.class);

    public static final String SESSION_KEY_USERINFO = AuthFilter.class.getName() + ".USERINFO";
    public static final String SESSION_KEY_AUTOLOGIN = AuthFilter.class.getName() + ".AUTOLOGIN";

    public static final String AUTOLOGIN_USERNAME = "auth/autologin:username";
    public static final String AUTOLOGIN_PASSWORD = "auth/autologin:password";

    protected void onBeforeExec() throws Exception {
        // забираем из сессии информацию о пользователе
        IUserInfo ui = (IUserInfo) getRequest().getSession().get(SESSION_KEY_USERINFO);

        // делаем этого пользователя текущим для запроса
        ui = getApp().service(AuthService.class).setCurrentUser(ui);

        if (ui.isGuest() && getApp().isDebug()) {
            // автологин, если не залогинен и отладочный режим
            devAutoLogin();
        }
    }

    protected void devAutoLogin() throws Exception {
        // autologin срабатывает только раз
        if (getSession().get(SESSION_KEY_AUTOLOGIN) != null) {
            return;
        }
        getSession().put(SESSION_KEY_AUTOLOGIN, true);
        //
        String un = getApp().getRt().getValueString(AUTOLOGIN_USERNAME);
        if (UtString.empty(un)) {
            return; // нет имени пользователя для autologin
        }

        String pw = getApp().getRt().getValueString(AUTOLOGIN_PASSWORD);
        //
        IUserInfo ui = null;
        try {
            log.info("autoLogin: " + un + "/" + pw);
            ui = (IUserInfo) getApp().service(ModelService.class).getModel().daoinvoke("Usr", "auth/login", un, pw);
        } catch (Exception e) {
            log.error("ERROR autoLogin", e);
            return; // не сработал
        }
        getApp().service(AuthService.class).setCurrentUser(ui);
        getSession().put(AuthFilter.SESSION_KEY_USERINFO, ui);
    }
}
