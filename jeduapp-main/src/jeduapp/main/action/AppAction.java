package jeduapp.main.action;

import mb.core.action.MbAppAction;

public class AppAction extends MbAppAction {

    public void index() throws Exception {
        render("/gsp/app.gsp");
    }

    protected String getAppGsp() {
        return "/gsp/app.gsp";
    }


}
