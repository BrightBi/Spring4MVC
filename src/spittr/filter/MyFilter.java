package spittr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 一、Filter基本工作原理
 * 1、Filter 程序是一个实现了特殊接口的 Java 类，与 Servlet 类似，也是由 Servlet 容器进行调用和执行的。
 * 2、当在 web.xml 注册了一个 Filter 来对某个 URL 进行处理时，它可以决定是否将请求继续传递给 Servlet 程序，以及对请求和响应消息是否进行修改。
 * 3、当 Servlet 容器根据 URL 准备调用某个 Servlet 时，如果发现 URL 上已经注册了一个 Filter，那么容器不再直接调用 Servlet，而是调用 Filter 的 doFilter 方法，再由 doFilter 方法决定是否继续调用 Servlet。
 * 4、在 Filter.doFilter 中通过调用 FilterChain.doFilter 方法来调用 Servlet，FilterChain 对象是通过 Filter.doFilter 方法的参数传递进来的。
 * 5、只要在 Filter.doFilter 方法中调用 FilterChain.doFilter 方法的语句前后增加某些程序代码，这样就可以在 Servlet 进行响应前后实现某些特殊功能。
 * 6、如果在 Filter.doFilter 方法中没有调用 FilterChain.doFilter 方法，则目标 Servlet 的 service 方法不会被执行，这样通过 Filter 就可以阻止某些非法的访问请求。
 * 
 * 二、Filter链
 * 1、在一个 Web 应用程序中可以注册多个 Filter 程序，每个 Filter 程序都可以对一个或一组 Servlet 程序进行拦截。
 *   如果有多个 Filter 程序都可以对某个 Servlet 程序的访问过程进行拦截，当针对该 Servlet 的访问请求到达时，Web 容器将把这多个 Filter 程序组合成一个 Filter 链（也叫过滤器链）。
 * 2、Filter 链中的各个 Filter 的拦截顺序与它们在 web.xml 文件中的映射顺序一致，上一个 Filter.doFilter 方法中调用 FilterChain.doFilter 方法将激活下一个 Filter 的 doFilter 方法，
 *   最后一个 Filter.doFilter 方法中调用的 FilterChain.doFilter 方法将激活目标 Servlet的service 方法。
 * 3、只要 Filter 链中任意一个 Filter 没有调用 FilterChain.doFilter 方法，则目标 Servlet 的 service 方法都不会被执行。
 * 
 * 三、Filter接口
 * 一个 Filter 程序就是一个 Java 类，这个类必须实现 Filter 接口。javax.servlet.Filter 接口中定义了三个方法：init、doFilter、destory。
 * 1、init 方法
 * （1）、在 Web 应用程序启动时，Web 服务器（Web 容器）将根据其 web.xml 文件的配置信息来创建每个注册的 Filter 的实例对象，并将其保存在内存中。
 * （2）、Web 容器创建 Filter 的实例对象后，将立即调用该 Filter 对象的 init 方法。init 方法在 Filter 生命周期中仅被执行一次，
 *       Web 容器在调用 init 方法时，会传递一个包含 Filter 的配置和运行环境信息的 FilterConfig 对象。public voic init(FilterConfig filterConfig) throws ServletException
 * （3）开发人员可以在 init 方法中完成与构造方法类似的初始化功能，要注意的是：如果初始化代码要使用到 FilterConfig 对象，这些代码只能在 init 方法中编写，
 *       而不能在构造方法中编写（尚未调用 init 方法，即并没有创建 FilterConfig 对象，要使用它则必然出错）。
 * 2、doFilter 方法
 * 当一个 Filter 对象能够拦截访问请求时，Servlet 容器将调用 Filter 对象的 doFilter 方法。
 * public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException.ServletException
 * 其中，参数 request 和 response 为 Web 容器或 Filter 链中上一个 Filter 传递过来的请求和响应对象；参数 chain 为代表当前 Filter 链的对象。
 * 3、destroy 方法
 * 该方法在 Web 容器卸载 Filter 对象之前被调用，也仅执行一次。可以完成与 init 方法相反的功能，释放被该 Filter 对象打开的资源，例如：关闭数据库连接和 IO 流。
 * 
 * 四、FilterChain接口
 * 该接口用于定义一个 Filter 链的对象应该对外提供的方法，这个接口只定义了一个 doFilter 方法。
 * public void doFilter(ServletRequest request, ServletResponse response) throws java.io.IOException.ServletException
 * FilterChain 接口的 doFilter 方法用于通知 Web 容器把请求交给 Filter 链中的下一个 Filter 去处理，如果当前调用此方法的 Filter 对象是Filter 链中的最后一个 Filter，那么将把请求交给目标 Servlet 程序去处理。
 * 
 * 五、FilterConfig 接口
 * 1、与普通的 Servlet 程序一样，Filter 程序也很可能需要访问 Servlet 容器。Servlet 规范将代表 ServletContext 对象和 Filter 的配置参数信息都封装到一个称为 FilterConfig 的对象中。
 * 2、FilterConfig 接口则用于定义 FilterConfig 对象应该对外提供的方法，以便在 Filter 程序中可以调用这些方法来获取 ServletContext 对象，以及获取在 web.xml 文件中为 Filter 设置的友好名称和初始化参数。
 * 3、FilterConfig 接口定义的各个方法：
 *         getFilterName 方法，返回 <filter-name> 元素的设置值。
 *         getServletContext 方法，返回 FilterConfig 对象中所包装的 ServletContext 对象的引用。
 *         getInitParameter 方法，用于返回在 web.xml 文件中为 Filter 所设置的某个名称的初始化的参数值。
 *         getInitParameterNames 方法，返回一个 Enumeration 集合对象。
 */
public class MyFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("MyFilter distory");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("MyFilter before");
		filterChain.doFilter(servletRequest, servletResponse);
		System.out.println("MyFilter after");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("MyFilter init");
	}

}
