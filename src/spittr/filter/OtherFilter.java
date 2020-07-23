package spittr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class OtherFilter implements Filter {
	@Override
	public void destroy() {
		System.out.println("OtherFilter distory");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("OtherFilter before");
		filterChain.doFilter(servletRequest, servletResponse);
		System.out.println("OtherFilter after");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("OtherFilter init");
	}
}
