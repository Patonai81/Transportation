package hu.webuni.transportation.web.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webuni.transportation.dto.AddressDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingResponseWrapper;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.List;

//@WebFilter("/api/addresses/*")
public class SearchResponseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.resetBuffer();
        ContentCachingResponseWrapper ccrw= new ContentCachingResponseWrapper(httpResponse);
        //old body:
        String content=new String(ccrw.getContentAsByteArray(), "utf-8");


        System.out.println(content);
        HttpServletResponseWrapper hsrw=new HttpServletResponseWrapper(httpResponse);
        //hsrw.getOutputStream().write("Szabcsika".asB);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
