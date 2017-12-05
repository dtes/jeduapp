package jeduapp.main.controller;

import jandcode.dbm.data.DataBox;
import jandcode.dbm.data.DataRecord;
import jandcode.dbm.data.DataStore;
import mb.core.controller.MbController;

public abstract class CustomController extends MbController {

    protected DataRecord daoinvoker(String domainName, String methodName, Object... args) throws Exception {
        return (DataRecord) ut.daoinvoke(domainName, methodName, args);
    }

    protected DataStore daoinvokes(String domainName, String methodName, Object... args) throws Exception {
        return (DataStore) ut.daoinvoke(domainName, methodName, args);
    }

    protected DataBox daoinvokeb(String domainName, String methodName, Object... args) throws Exception {
        return (DataBox) ut.daoinvoke(domainName, methodName, args);
    }

}
