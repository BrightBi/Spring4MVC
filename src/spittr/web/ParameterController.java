package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import spittr.domain.Spittle;

@Controller
@RequestMapping({"/parameter"})
public class ParameterController {

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/parameter
	@RequestMapping(method=RequestMethod.GET)
	public String requestParam(@RequestParam(value="number", defaultValue="7") int number,
			@RequestParam(value="name", defaultValue="bi") String name) {
		System.out.println("Name:" + name + ", Number:" + number);
		return "info";
	}
	
	// http://127.0.0.1:8080/Spring4MVC/parameter/path/1001
	@RequestMapping(value="/path/{id}", method=RequestMethod.GET)
	public String pathParam(@PathVariable("id") int id) {
		System.out.println("Id:" + id);
		return "info";
	}
	
	// http://127.0.0.1:8080/Spring4MVC/parameter/object
	@RequestMapping(value="/object", method=RequestMethod.POST)
	public String objectParam(Spittle spittle) {
		System.out.println("Spittle:" + spittle);
		/*
		 *  返回的视图中带有 redirct: 表示重定向到指定到 URL，这里重定向到 /parameter/info。forward 也是类似。
		 *  当前映射是处理 POST 的，而 redirect 以后，请求就变成 GET 了，需要注意。
		 */
		return "redirect:/parameter/here";
	}
	
	// http://127.0.0.1:8080/Spring4MVC/parameter/redirect
	@RequestMapping(value="/redirect", method=RequestMethod.POST)
	public String redirectParam(Spittle spittle, Model model) {
		System.out.println("Spittle:" + spittle);
		/*
		 *  Model 中添加的数据会以 请求参数形式传递给 redirect 的 URL
		 *  http://127.0.0.1:8080/Spring4MVC/parameter/redirect?id=xxxx&message=xxxx
		 */
		model.addAttribute("id", spittle.getId());
		model.addAttribute("message", spittle.getMessage());
		return "redirect:/parameter/here";
	}
	
	// http://127.0.0.1:8080/Spring4MVC/parameter/flash
	@RequestMapping(value="/flash", method=RequestMethod.POST)
	public String flashParam(Spittle spittle, RedirectAttributes model) {
		System.out.println("Spittle:" + spittle);
		/*
		 *  Flash 中添加的数据会被放到会话中，直到重定向后被取走，并从会话中转移到 model 中。
		 */
		model.addAttribute("id", spittle.getId());
		model.addAttribute("message", spittle.getMessage());
		model.addFlashAttribute("spittle", spittle);
		return "redirect:/parameter/flash-here";
	}
	
	@RequestMapping(value="/here", method=RequestMethod.GET)
	public String redirectHere(Spittle spittle) {
		System.out.println("Spittle:" + spittle);
		return "info";
	}
	
	@RequestMapping(value="/flash-here", method=RequestMethod.GET)
	public String flashHere(@RequestParam(value="id", defaultValue="7") int id, Model model) {
		System.out.println("Id:" + id);
		System.out.println("Spittle:" + model.asMap().get("spittle"));
		return "info";
	}
	
	/*
	 *  参数校验需要配合在 Spittle 类上加校验注解。
	 *  @Valid 注解到参数是需要校验的，Errors 必须紧跟着被校验的参数。
	 *  使用 @Valid 需要引入 validation-api-2.0.1.Final.jar，
	 *  同时要保证在类路径下包含这个 Java API 的实现，比如 Hibernate Validator。
	 */
	// http://127.0.0.1:8080/Spring4MVC/parameter/valid
	@RequestMapping(value="/valid", method=RequestMethod.POST)
	public String validParam(@Valid Spittle spittle, Errors errors) {
		System.out.println("Spittle:" + spittle);
		
		if (errors.hasErrors()) {
			System.out.println("Error");
			return "info";
		} else {
			System.out.println("Success");
			return "home/home";
		}
	}
}
