package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * @RequestMapping({"/home", "/homePage"}) 表示访问 /home 或者 /homePage 都会映射到找个类上
 *
 */
@Controller
@RequestMapping({"/home", "/homePage"})
public class HomeController {

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/home/detail 或 http://127.0.0.1:8080/Spring4MVC/homePage/detail
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public String home() {
		return "info";
	}
}
