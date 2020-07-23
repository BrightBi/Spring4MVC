package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import spittr.repository.SecurityMethods;

@Controller
@RequestMapping({ "/security" })
public class SecurityController {

	@Autowired
	SecurityMethods securityMethods;

	/*
	 * mvc 路径下的要参考 spittr.config.SecurityConfig 中的访问权限配置。
	 */
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/mvc/one
	@RequestMapping(value = "/mvc/one", method = RequestMethod.GET)
	public String one() {
		System.out.println("Path:/mvc/one");
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/mvc/two
	@RequestMapping(value = "/mvc/two", method = RequestMethod.GET)
	public String two() {
		System.out.println("Path:/mvc/two");
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/mvc/three
	@RequestMapping(value = "/mvc/three", method = RequestMethod.GET)
	public String three() {
		System.out.println("Path:/mvc/three");
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/mvc/regex/1
	@RequestMapping(value = "/mvc/regex/1", method = RequestMethod.GET)
	public String regex1() {
		System.out.println("Path:/mvc/regex/1");
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/mvc/regex/2
	@RequestMapping(value = "/mvc/regex/2", method = RequestMethod.GET)
	public String regex2() {
		System.out.println("Path:/mvc/regex/2");
		return "info";
	}

	// 访问路径 https://127.0.0.1:8080/Spring4MVC/security/mvc/https
	// (当前 mvc 还不支持 https，所以即使用了 https 也会报错。先知道有这么个安全检查就好。)
	@RequestMapping(value = "/mvc/https", method = RequestMethod.GET)
	public String https() {
		System.out.println("Path:/mvc/https");
		return "info";
	}

	/*
	 * method 路径下的要参考 spittr.repository.SecurityMethods 中的 权限配置。
	 */
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/secured
	@RequestMapping(value = "/method/secured", method = RequestMethod.GET)
	public String secured() {
		System.out.println(securityMethods.secured());
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/rolesAllowed
	@RequestMapping(value = "/method/rolesAllowed", method = RequestMethod.GET)
	public String rolesAllowed() {
		System.out.println(securityMethods.rolesAllowed());
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/preAuthorize
	@RequestMapping(value = "/method/preAuthorize", method = RequestMethod.GET)
	public String preAuthorize() {
		System.out.println(securityMethods.preAuthorize());
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/preAuthorizeSpEL
	@RequestMapping(value = "/method/preAuthorizeSpEL", method = RequestMethod.GET)
	public String preAuthorizeSpEL(@RequestParam(value = "name") String name) {
		System.out.println(securityMethods.preAuthorizeSpEL(name));
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/postAuthorize
	@RequestMapping(value = "/method/postAuthorize", method = RequestMethod.GET)
	public String postAuthorize(@RequestParam(value = "name") String name) {
		System.out.println(securityMethods.postAuthorize(name));
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/postFilter
	@RequestMapping(value = "/method/postFilter", method = RequestMethod.GET)
	public String postFilter() {
		System.out.println(securityMethods.postFilter());
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/preFilter
	@RequestMapping(value = "/method/preFilter", method = RequestMethod.GET)
	public String preFilter() {
		List<String> list = new ArrayList<>();
		list.add("123");
		list.add("456");
		list.add("123456");
		System.out.println(securityMethods.preFilter(list, "1234567890"));
		return "info";
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/security/method/selfPermissionCheck
	@RequestMapping(value = "/method/selfPermissionCheck", method = RequestMethod.GET)
	public String selfPermissionCheck() {
		List<String> list = new ArrayList<>();
		list.add("123");
		list.add("456");
		list.add("123456");
		System.out.println(securityMethods.selfPermissionCheck(list));
		return "info";
	}
}
