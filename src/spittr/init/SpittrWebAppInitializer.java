package spittr.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import spittr.config.RootConfig;
import spittr.config.WebConfig;
/**
 * 
 * Servlet 3.0 环境及以上版本的 service 会在类路径中查找实现了 javax.servlet.ServletContainerInitializer 接口的类，如果发现了就用该类配置 Servlet 容器。
 * 而 Spring 提供了该类的实现 SpringServletContainerInitializer，这个类反过来又会去查找实现了 WebApplicationInitializer 的类，并将配置任务交给它。
 * AbstractAnnotationConfigDispatcherServletInitializer 就是 WebApplicationInitializer 一个基础实现，SpittrWebAppInitializer 又实现了它，
 * 所以 Servlet 3.0 容器（tomcat7以上）会找到 SpittrWebAppInitializer 并用它配置 Servlet 容器。
 * 
 * getServletConfigClasses 与 getRootConfigClasses 解析
 * Spring Web 中的一个应用上下文是由 DispatcherServlet 创建的，还有一个是由 ContextLoaderListener 创建的。
 * AbstractAnnotationConfigDispatcherServletInitializer 会同时创建 DispatcherServlet 和 ContextLoaderListener。
 * 通常我们希望 DispatcherServlet 加载 Web 组件，比如：service controller ...
 *            ContextLoaderListener 加载其他 bean，比如：驱动后端的中间层和数据层组件
 * getServletConfigClasses() 返回带有 @Configuration 注解的类，将用来定义 DispatcherServlet 应用上下文中的 bean。
 * getRootConfigClasses() 返回带有 @Configuration 注解的类，将用来定义 ContextLoaderListener 应用上下文中的 bean。
 * 
 * 等价的 xml 配置
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
	<web-app version="3.0"
	    xmlns="http://java.sun.com/xml/ns/javaee"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

	  <context-param>
	    <!-- 此处配置 spring 通过 Java 配置 bean 的方式加载根配置 bean -->
	    <param-name>contextClass</param-name>
	    <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	  </context-param>
	  <context-param>
	    <!-- 此处配置具体哪个标注了 @Configuration 的 Java 类 -->
	    <param-name>contextConfigLocation</param-name>
	    <param-value>spittr.config.RootConfig</param-value>
	  </context-param>

	  <!-- 配置 ContextLoaderListener 作为 listener，他会加载业务层的 Spring 配置文件，需要结合上面的 <context-param> 使用 -->
	  <!-- 在 <context-param> 中配置 <param-name>contextClass</param-name> 和  <param-value /> -->
	  <listener>
	    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	  </listener>

	  <servlet>
	    <!-- 此处配置 spring 通过 Java 配置 bean 的方式加载 DispatcherServlet 的 bean -->
	    <servlet-name>spring</servlet-name>
	    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	    <!-- 此处配置通过 标注了 @Configuration 的 Java 类来加载 spring DispatcherServlet 的 bean 配置 -->
	    <init-param>
	        <param-name>contextClass</param-name>
	        <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	    </init-param>
	    <!-- 此处配置具体哪个标注了 @Configuration 的 Java 类 -->
	    <init-param>
	        <param-name>contextConfigLocation</param-name>
	        <param-value>spittr.config.WebConfig</param-value>
	    </init-param>
	    <init-param>
	        <param-name>fileEncoding</param-name>
	        <param-value>UTF-8</param-value>
	    </init-param>
	    <load-on-startup>1</load-on-startup>
	  </servlet>
	  <servlet-mapping>
	    <servlet-name>spring</servlet-name>
	     <!-- 此处配置 url-pattern 为 / 表示所有请求都将由 spring 对应的 DispatcherServlet 来处理 -->
	    <url-pattern>/</url-pattern>
	  </servlet-mapping>
	</web-app>
 *
 * 可能遇到的问题。启动时候报异常：More than one fragment with the name [spring_web] was found
 * Servlet3.0的改进之一就是使得我们能够将框架和库插入到web应用程序中。这种可插入性减少了配置，并且提高了web应用程序的模块化。
 * Servlet3.0是通过web模块布署描述片段（简称web片段）来实现插入性的。 一个web片段就是web.xml文件的一部分，被包含在框架特定的Jar包的META-INF目录中。
 * Web片段使得该框架组件逻辑上就是web应用程序的一部分，不需要编辑 web 布署描述文件（web.xml）。
 * Web片段的根元素是 <web-fragment>,而且文件名必须叫做 web-fragment.xml。容器只会在放在 WEB-INF\lib 目录下的 Jar 包中查找 web-fragment.xml 文件。
 * 如果这些 Jar 包含有 web-fragment.xml 文件，容器就会装载需要的类来处理他们。 在web.xml中，我们要求 Servlet 的 name 必须唯一。
 * 同样的，在 web.xml 和所有的 web 片段中，Servlet 的 name 也必须唯一。<web-fragment> 可以指定绝对顺序或者相对顺序，那么同名的后出现的就会被忽略。
 * 如果不指定 <web-fragment> 顺序关系，那么容器认为所有片段都是相互独立的，且不能同名。
 * 
 * 由于导入 spring-web 时候也误将 对应的源码导入了 lib。源码和 jar 包都有一个相同的 <web-fragment>，导致重名冲突。
 * 将源码的删掉，只留有效的 jar 包就解决问题了。
 */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// 指定配置类，DispatcherServlet 在加载应用上下文时候，加载在 WebConfig 中配置的 bean
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// 将 DispatcherServlet 映射到 "/"，会处理进入应用的所有请求
		return new String[] { "/" };
	}

	/*
	 * 配置 MultipartConfigElement 
	 * new MultipartConfigElement("/opt/active", 2097152, 4194304, 0)
	 * 第一个参数是上传文件的目录；第二个是文件大小；第三个是整个请求的大小；第四个参数是文件大小超过指定容量是否写入临时文件路径，0 表示不写入。
	 * 对于 StandardServletMultipartResolver 必须配置 location
	 * 
	 * 对应 xml 配置
		<servlet>
			<servlet-name>spring</servlet-name>
			<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
			<load-on-startup>1</load-on-startup>
			<multipart-config>
				<location>/opt/active</location>
				<max-file-size>2097152</max-file-size>
				<max-request-size>4194304</max-request-size>
			<multipart-config>
		</servlet>
	 * 另外 customizeRegistration 方法不只可以配置 MultipartConfigElement，它主要是用来配置 DispatcherServlet 的，
	 * 配置 DispatcherServlet 是通过设置 Dynamic 来完成。
	 * 我们还可以调用 registration.setLoadOnStartup(); 设置 <load-on-startup>。
	 * registration.setInitParameter(); 设置初始化参数。
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement("/opt/active", 2097152, 4194304, 0));
	}

}
