package mk.ukim.finki.wp.task1.web.servlets.impl;

import lombok.SneakyThrows;
import mk.ukim.finki.wp.task1.web.servlets.CachedBodyServletInputStream;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class HttpServletRequestWrapperImpl extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public HttpServletRequestWrapperImpl(HttpServletRequest request) throws IOException, IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }


}
