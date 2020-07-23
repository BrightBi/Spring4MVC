package spittr.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/**
 * AbstractSecurityWebApplicationInitializer 实现了 WebApplicationInitializer，
 * 所以 Spring 会发现它并用它在 web 容器中注册 DelegatingFilterProxy
 * 
 * 等效 web.xml 配置
 * <filter>
 *     <filter-name>springSecurityFilterChain</filter-name>
 *     <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
 * </filter>
 * DelegatingFilterProxy 会拦截请求并委托给一个名为 springSecurityFilterChain 的 Filter bean 来处理。
 * 
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer{

}
