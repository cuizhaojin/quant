/**
 * 
 */
package com.hexun.quant.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author lisen
 *
 */
@Component
public class CrossSiteFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setCharacterEncoding("utf-8");
		// HttpServletRequest Request = (HttpServletRequest) servletRequest;
		// HttpSession session = new HttpSession();
		// String origin = (String) servletRequest.getRemoteHost() + ":"
		// + servletRequest.getRemotePort();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

	public static void main(String[] args) throws Exception {

	}
}
