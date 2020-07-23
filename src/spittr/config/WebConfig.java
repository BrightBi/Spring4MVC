package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import spittr.interceptor.InterceptorOne;
import spittr.interceptor.InterceptorTwo;

/**
 * @EnableWebMvc 表示启动 Spring Web MVC
 * @ComponentScan 只让它去扫描 spittr.web 中的 controller，
 * 其他的 bean 统一由 RootConfig 来扫描加载。
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"spittr.web"})
public class WebConfig extends WebMvcConfigurerAdapter{

	// 配置 JSP 视图解析器
	@Bean
	public ViewResolver viewResolver () {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	/*
	 * 处理文件上传下载时候必须要配置上这个 bean。
	 * 具体实现类用 CommonsMultipartResolver 或 StandardServletMultipartResolver 都可以，
	 * StandardServletMultipartResolver 更优，它不依赖其他项目，但要求在 Servlet3.0 以上用。
	 * 除此以外还必须配置 MultipartConfigElement (在 SpittrWebAppInitializer 中配置)
	 * 
	 * 对应 xml 配置
		<bean id="multipartResolver" class="org.springframework.web.multipart.support.StandardServletMultipartResolver" />
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	// 配置静态资源的处理
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 告知 DispatcherServlet 对于静态资源的请求转发给 Servlet 容器中默认的 servlet，
		// 而不使用 DispatcherServlet 来处理静态资源请求。
		configurer.enable();
	}
	
	// 配置静自定义拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		/*
		 * Filter 和 Interceptor 的区别
		 * Filter 是基于函数回调（doFilter()方法）的，而 Interceptor 则是基于 Java 反射的（AOP思想）。
		 * Filter 依赖于 Servlet 容器，而 Interceptor 不依赖于 Servlet 容器。
		 * Filter 对几乎所有的请求起作用，而 Interceptor 只能对 action 请求起作用。
		 * Interceptor 可以访问 Action 的上下文，值栈里的对象，而 Filter 不能。
		 * 在 action 的生命周期里，Interceptor 可以被多次调用，而 Filter 只能在容器初始化时调用一次。
		 * 
		 * 拦截器和过滤器执行顺序：
		 * 1、Filter.init();
		 * 2、Filter.doFilter(); before doFilter
		 * 3、HandlerInterceptor.preHandle();
		 * 4、Controller 方法执行
		 * 5、HandlerInterceptor.postHandle();
		 * 6、DispatcherServlet 视图渲染
		 * 7、HandlerInterceptor.afterCompletion();
		 * 8、Filter.doFilter(); after doFilter
		 * 9、Filter.destroy();
		 */

		//添加自己的拦截器和其拦截的路径
		registry.addInterceptor(new InterceptorOne()).addPathPatterns("/**");
		registry.addInterceptor(new InterceptorTwo()).addPathPatterns("/home/**");
	}
}
