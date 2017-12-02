package jeduapp.main.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр решает проблему с CORS
 */
public class SimpleCorsFilter implements Filter {
    private static final Log LOG = LogFactory.getLog(SimpleCorsFilter.class);
    private String corsEnableUrl;
    private String allowedHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            corsEnableUrl = filterConfig.getInitParameter("cors.allowed.url");
            allowedHeaders = filterConfig.getInitParameter("cors.allowed.headers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getRequestURI().contains(corsEnableUrl)) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Access-Control-Allow-Headers", allowedHeaders);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
