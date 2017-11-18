package jeduapp.main.controller;

import jandcode.dbm.data.DataBox;
import jandcode.utils.UtCnv;
import mb.core.controller.MbController;

public class TestController extends MbController{

    public void index(DataBox params) throws Exception {
        renderFrame("Test", UtCnv.toMap(

        ));
    }

}
