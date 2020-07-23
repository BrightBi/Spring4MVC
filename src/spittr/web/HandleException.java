package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import spittr.domain.Spittle;
import spittr.exception.ExceptionOne;
import spittr.exception.ExceptionThree;
/**
 * @ControllerAdvice 注释的类需要包含一个或多个被 @ExceptionHandler @InitBinder @ModelAttribute 标注的方法。
 * 以这种方式标注的方法会被应用到应用程序中所有被 @RequestMapping 注解的方法。
 * @ControllerAdvice 本身使用了 @Component，所以它会被组件的自动扫描获取到
 */
@ControllerAdvice
public class HandleException {

	@ExceptionHandler(ExceptionOne.class)
	public String handleExceptionOne() {
		System.out.println("HandleException handleExceptionOne");
		// 返回的是视图名，会类似 Controller 返回视图的方式处理
		return "home/error";
	}
	
	/*
	 * @ResponseStatus 表示在头信息上附带上状态码。
	 * @ResponseBody 表示以资源形式返回。
	 */
	@ExceptionHandler(ExceptionThree.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Spittle handleExceptionThree() {
		return new Spittle();
	}
}
