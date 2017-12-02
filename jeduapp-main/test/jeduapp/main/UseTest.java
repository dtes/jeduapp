package jeduapp.main;

import jandcode.utils.UtString;
import org.junit.Test;

import java.util.regex.Pattern;

public class UseTest {

    @Test
    public void testMd5() {
        System.out.println(UtString.md5Str("111"));
    }

    @Test
    public void testPattern() throws Exception {
        Pattern p = Pattern.compile(".*api/.*");
        System.out.println(p.matcher("localhost:8080/wax/app/api/getSomething").matches());
    }
}
