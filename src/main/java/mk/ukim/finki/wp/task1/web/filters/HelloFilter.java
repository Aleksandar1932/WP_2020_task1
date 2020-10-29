package mk.ukim.finki.wp.task1.web.filters;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;

@WebFilter(filterName = "HelloFilter", urlPatterns = "/hello")
public class HelloFilter implements Filter {
    public void destroy() {
    }

    private String readJsonFromBody(ServletInputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletRequestWrapper wrapper = new ServletRequestWrapper((HttpServletRequest) req);

        String json = readJsonFromBody(req.getInputStream());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject == null) {
            // Can be done with custom exception
            throw new RuntimeException();
        }

        wrapper.setAttribute("jsonMap", jsonObject);
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
