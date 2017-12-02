package jeduapp.main.filter;

import jandcode.web.JcServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomServlet extends JcServlet {

    private String methodOptions = "OPTIONS";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getMethod().contains(methodOptions)) {
            super.service(request, response);
        }
    }

}
