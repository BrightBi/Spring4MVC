package spittr.init;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import spittr.filter.MyFilter;
import spittr.filter.OtherFilter;
import spittr.servlet.MyServlet;
import spittr.servlet.OurServlet;

public class MyServletInitializer implements WebApplicationInitializer {

	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/*
		 * 通过实现 WebApplicationInitializer 通过 onStartup 方法添加自己的 Servlet
		 * 
		 * 实验发现 如果 Spring 中的 URL mapping 跟自定的一致，会优先使用自定义的。
		 * 即 Spring 中配置了 /self/home，自定义的也配置了 /self/home，那么会用自定义的来处理这个 URL 请求。
		 */
		Dynamic myServlet = servletContext.addServlet("myServlet", MyServlet.class);
		myServlet.addMapping("/self/**");
		
		Dynamic ourServlet = servletContext.addServlet("ourServlet", OurServlet.class);
		ourServlet.addMapping("/my/**");
		
		/*
		 * 通过实现 WebApplicationInitializer 通过 onStartup 方法添加自己的 Filter
		 * servletContext.addFilter("myFilter", MyFilter.class) 返回类型是
		 * javax.servlet.FilterRegistration.Dynamic
		 * 此处添加顺序决定了 filter 最终执行的顺序。xml 是根据配置的顺序决定 filter 最终执行顺序。
		 */
		servletContext.addFilter("myFilter", MyFilter.class).addMappingForUrlPatterns(null, false, "/home/*");
		servletContext.addFilter("otherFilter", OtherFilter.class).addMappingForUrlPatterns(null, false, "/home/*");
	}
}
