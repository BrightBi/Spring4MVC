package spittr.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterceptorTwo extends HandlerInterceptorAdapter {
	
	public InterceptorTwo() {
		System.out.println("InterceptorTwo");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 * 这里面写一些到达Controller之前需要进行拦截的代码。
		 * true 表示放行,false 表示拦截
		 */
		System.out.println("InterceptorTwo.preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*
		 * postHandle 方法是在执行完 Controller 层代码之后，DispatcherServlet 进行视图的渲染之前执行，
		 * 因此可以对 ModelAndView 对象进行处理；afterCompletion 方法是在 DispatcherServlet 进行视图的渲染之后执行调用
		 */
		System.out.println("InterceptorTwo.postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		/*
		 * afterCompletion 方法是在 DispatcherServlet 进行视图的渲染之后执行调用
		 */
		System.out.println("InterceptorTwo.afterCompletion");
	}
}
