package edu.miu.minimarket.filter;

import jakarta.servlet.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SomeFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("BEFORE THE FILTER CHAIN");
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
