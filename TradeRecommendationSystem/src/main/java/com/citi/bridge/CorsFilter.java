package com.citi.bridge;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		addCORSHeaders(response, request);
		chain.doFilter(req, res);
	}

	public static void addCORSHeaders(HttpServletResponse response, HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
		response.addHeader("Access-Control-Allow-Headers", "x-citiportal-LoginID, Content-Type, Accept, x-socketId");
		response.addHeader("Access-Control-Expose-Headers", "FileName");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, HEAD, DELETE");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		String requestHeaders = request.getHeader("Access-Control-Request-Headers");
		if (requestHeaders != null)
			response.addHeader("Access-Control-Allow-Headers", requestHeaders);
	}
	
	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}

}
