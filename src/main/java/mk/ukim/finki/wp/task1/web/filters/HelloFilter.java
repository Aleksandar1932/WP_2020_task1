package mk.ukim.finki.wp.task1.web.filters;

import mk.ukim.finki.wp.task1.web.servlets.impl.HttpServletRequestWrapperImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

@WebFilter(filterName = "HelloFilter", urlPatterns = "/hello")
public class HelloFilter implements Filter  {

    private byte[] cachedBody;

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

//        ServletRequestWrapper wrapper = new ServletRequestWrapper((HttpServletRequest) req);

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


        HttpServletRequest cachedBodyHttpServletRequest = new HttpServletRequestWrapperImpl((HttpServletRequest) req);
        cachedBodyHttpServletRequest.setAttribute("jsonMap", jsonObject);

        chain.doFilter(cachedBodyHttpServletRequest, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
