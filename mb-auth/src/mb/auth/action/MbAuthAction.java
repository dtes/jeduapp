package mb.auth.action;

import jandcode.auth.AuthService;
import jandcode.auth.IUserInfo;
import jandcode.dbm.ModelService;
import jandcode.web.WebAction;
import mb.auth.filter.AuthFilter;

public class MbAuthAction extends WebAction {
    public void login() throws Exception {
        String loginField = getApp().getRt().getChild("app/conf/loginField").getValueString("value");
        String login = getParams().getValueString(loginField);
        String password = getParams().getValueString("passwd");

        IUserInfo ui = (IUserInfo) getApp().service(ModelService.class).getModel().daoinvoke("Usr", "auth/login",
                login, password);

        getApp().service(AuthService.class).setCurrentUser(ui);
        getSession().put(AuthFilter.SESSION_KEY_USERINFO, ui);
        getRequest().getOutWriter().write("ok");
    }

    public void logout() throws Exception {
        getApp().service(AuthService.class).setCurrentUser(null);
        getSession().put(AuthFilter.SESSION_KEY_USERINFO, null);
        getRequest().getOutWriter().write("ok");
    }
}
