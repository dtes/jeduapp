package mb.auth.impl;

import jandcode.app.IActivate;
import jandcode.app.IAfterActivate;
import jandcode.app.IAppStartup;
import jandcode.app.ServiceContainer;
import jandcode.auth.*;
import jandcode.auth.impl.PrivImpl;
import jandcode.auth.impl.RoleImpl;
import jandcode.utils.ListNamed;
import jandcode.utils.rt.Rt;

import java.util.Collection;

public class AuthServiceImpl extends AuthService implements IActivate, IAfterActivate, IAppStartup {

    protected ThreadLocal<IUserInfo> userInfoThreaded = new ThreadLocal<IUserInfo>();
    protected IUserInfo userInfoGlobal;

    // узел 'auth/priv/PRIVNAME', из которого загружается дерево привелегий
    protected String privName = "default";

    // доступные привелегии
    protected ListNamed<PrivImpl> privs = new ListNamed<PrivImpl>();

    // доступные роли
    protected ListNamed<RoleImpl> roles = new ListNamed<RoleImpl>();

    protected ServiceContainer services = new ServiceContainer();

    ///
    protected void onSetRt(Rt rt) {
        super.onSetRt(rt);
        // привелегии
        loadPrivs("auth/priv/" + getPrivName());
        // сервисы
        services.addAll(rt.findChild("service"), getApp().getObjectFactory());
    }

    public void activate() throws Exception {
        for (IActivate a : services.impl(IActivate.class)) {
            a.activate();
        }
    }

    public void afterActivate() throws Exception {
        for (IAfterActivate a : services.impl(IAfterActivate.class)) {
            a.afterActivate();
        }
    }

    public void appStartup(Object appInstance) throws Exception {
        for (IAppStartup a : services.impl(IAppStartup.class)) {
            a.appStartup(appInstance);
        }
    }

    ///

    public <A extends Object> A service(Class<A> clazz) {
        return services.get(clazz);
    }

    public ServiceContainer getServices() {
        return services;
    }

    ///

    public UserInfoImpl createUserInfo() {
        return new UserInfoImpl(this);
    }

    protected IUserInfo createGuest() {
        UserInfoImpl z = createUserInfo();
        z.setId(0);
        z.setGuest(true);
        z.setName("guest");
        return z;
    }

    public IUserInfo getCurrentUser() {
        if (isThreaded()) {
            IUserInfo z = userInfoThreaded.get();
            if (z == null) {
                z = createGuest();
                setCurrentUser(z);
            }
            return z;
        } else {
            if (userInfoGlobal == null) {
                userInfoGlobal = createGuest();
            }
            return userInfoGlobal;
        }
    }

    public IUserInfo setCurrentUser(IUserInfo user) {
        if (isThreaded()) {
            userInfoThreaded.set(user);
        } else {
            userInfoGlobal = user;
        }
        return getCurrentUser();
    }

    //////


    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName;
    }

    public ListNamed<IPriv> getPrivs() {
        return (ListNamed) privs;
    }

    /**
     * Загрузка дерева привелегий из указанного узла
     */
    protected void loadPrivs(String rtpath) {
        privs.clear();
        Rt rt = getApp().getRt().findChild(rtpath);
        if (rt == null) {
            log.info("Not found rt for load privs: " + rtpath);
            return;
        }
        internal_loadPrivs(privs, null, rt);
    }

    private void internal_loadPrivs(ListNamed<PrivImpl> privs, IPriv own, Rt rt) {
        Rt x = rt.findChild("priv");
        if (x == null) {
            return;
        }
        for (Rt x1 : x.getChilds()) {
            //
            PrivImpl p = (PrivImpl) getApp().getObjectFactory().create(x1, PrivImpl.class);
            p.setName(p.getName().toLowerCase());
            privs.add(p);
            if (own != null) {
                p.setParentName(own.getName());
            }
            //
            internal_loadPrivs(privs, p, x1);
        }
    }

    //////

    public ListNamed<IRole> getRoles() {
        return (ListNamed) roles;
    }

    public void updateRoles(Collection<IRole> roles) {
        synchronized (this) {
            this.roles.clear();
            for (IRole r : roles) {
                this.roles.add((RoleImpl) r);
            }
        }
    }

    //////

    public boolean checkTarget(IUserInfo ui, String target, boolean generateErrors) {
        AuthTarget t = new AuthTarget(target);
        for (ICheckTarget ct : services.impl(ICheckTarget.class)) {
            try {
                ct.checkTarget(ui, t);
            } catch (Exception e) {
                if (!generateErrors) {
                    return false;
                }
                throw wrapError(e, t);
            }
        }
        return true;
    }

    /**
     * Оборачивание ошибки e в IErrorAccessDenied
     */
    protected RuntimeException wrapError(Exception e, AuthTarget t) {
        if (e instanceof IErrorAccessDenied) {
            return (RuntimeException) e;
        }
        return new XErrorAccessDeniedWrap(e, t);
    }

}