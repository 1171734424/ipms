package com.ideassoft.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements javax.servlet.Filter {

    protected String urlencoding;
    protected String appencoding;
    protected FilterConfig filterConfig = null;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig config) {
        this.filterConfig = config;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.urlencoding = filterConfig.getInitParameter("urlencoding");
        this.appencoding = filterConfig.getInitParameter("appencoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (appencoding != null && appencoding.length() > 0) {
            request.setCharacterEncoding(appencoding);
            response.setContentType("text/html;charset=" + appencoding);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        this.urlencoding = null;
        this.appencoding = null;
        this.filterConfig = null;
    }
}
