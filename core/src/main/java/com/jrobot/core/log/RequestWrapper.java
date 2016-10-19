package com.jrobot.core.log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.jrobot.core.utils.IOUtils;


public class RequestWrapper extends HttpServletRequestWrapper {
    private final ServletInputStream inputStream;
    private final byte[] body;
    private BufferedReader reader;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtils.bytes(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public String getCharacterEncoding() {
        String defaultEncoding = super.getCharacterEncoding();
        return defaultEncoding != null ? defaultEncoding : "UTF-8";
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    public byte[] getBody() {
        return body;
    }

    private static class RequestCachingInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
