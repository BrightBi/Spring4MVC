package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spittr.exception.ExceptionOne;
import spittr.exception.ExceptionTwo;

@Controller
@RequestMapping({ "/exception" })
public class ExceptionController {

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/exception/one
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public String exceptionOne() throws ExceptionOne {
		System.out.println("ExceptionOne");
		throw new ExceptionOne();
	}

	// 访问路径 http://127.0.0.1:8080/Spring4MVC/exception/two
	@RequestMapping(value = "/two", method = RequestMethod.GET)
	public String exceptionTwo() throws ExceptionTwo {
		System.out.println("ExceptionTwo");
		throw new ExceptionTwo();
	}
	
	/*
	 * 被 @RequestMapping 注解的类的内部被 @ExceptionHandler 注解的方法，可以处理当前类内部其他被 @RequestMapping 注解的方法抛出的异常。
	 * 但是处理不了其他类中被 @RequestMapping 注解的方法抛出的异常。这跟 @ControllerAdvice 注解的类中被 @ExceptionHandler 注解的方法不一样。
	 */
	@ExceptionHandler(ExceptionTwo.class)
	public String handleExceptionTwo() {
		System.out.println("ExceptionController handleExceptionTwo");
		// 返回的是视图名，会类似 Controller 返回视图的方式处理
		return "home/error";
	}
}
