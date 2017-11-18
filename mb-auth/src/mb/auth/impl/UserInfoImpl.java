package mb.auth.impl;

import jandcode.auth.AuthService;
import jandcode.auth.IPriv;
import jandcode.auth.IRole;
import jandcode.auth.IUserInfo;
import jandcode.utils.HashSetNoCase;
import jandcode.utils.ListNamed;
import jandcode.utils.Named;
import jandcode.utils.UtString;
import jandcode.utils.variant.VariantMap;

public class UserInfoImpl extends Named implements IUserInfo {

    protected AuthService authService;
    protected long id;
    protected String username = "";
    protected String email = "";
    protected String fullname = "";
    protected boolean guest;
    protected boolean locked;
    protected VariantMap attrs = new VariantMap();
    protected HashSetNoCase roles = new HashSetNoCase();

    public UserInfoImpl(AuthService authService) {
        this.authService = authService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return UtString.empty(fullname) ? getName() : fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public VariantMap getAttrs() {
        return attrs;
    }

    public boolean hasRole(String name) {
        return false;
    }

    public boolean hasPriv(String name) {
        return false;
    }

    public ListNamed<IPriv> getPrivs() {
        return null;
    }

    public ListNamed<IRole> getRoles() {
        return null;
    }

    public boolean checkTarget(String target, boolean generateError) {
        return getAuthService().checkTarget(this, target, generateError);
    }

    public Object getAttr(String name) {
        return attrs.get(name);
    }


}