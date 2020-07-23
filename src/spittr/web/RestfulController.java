package spittr.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import spittr.domain.Book;
import spittr.domain.Spittle;
import spittr.exception.ExceptionThree;
/**
 * @RestController 跟 @Controller 区别在于，可以省略方法返回值前面的 @ResponseBody。
 * @RestController 注解某个类，这个类中的方法的返回值都会自动做资源转换。所以可以省去 @ResponseBody 注解。
 * 
 * @ResponseBody 会告诉 Spring 将返回对象做为资源返回，并且返回对象会被转换成客户端所接受的资源形式（json,xml,...），而不是去做视图转换。
 * DispatcherServlet 会参考请求中的 accept 头信息决定返回对象转换成什么资源形式。
 * 如果 accept 是 application/json，那么将以 json 形式返回。
 * 如果 accept 是 text/xml，那么将以 xml 形式返回。
 * 
 * @RequestMapping(value="/j", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
 * produces = MediaType.APPLICATION_JSON_VALUE 表示该方法只接受 Accept 是 application/json 的请求。
 * 也就是说如果一个请求它的 URL(http://127.0.0.1:8080/Spring4MVC/rest/j) 跟 RequestMethod(GET) 都能匹配上，
 * 但是 Accept 不是 application/json，那么这个方法也不会处理这个请求。
 * (Accept 标识客户端希望接受的数据类型，比如 Accept = application/json，意味着客户端告知服务端，我希望你返回给我 json 格式的数据)
 * 
 * @RequestMapping(value="/j", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
 * consumes = MediaType.APPLICATION_JSON_VALUE 表示该方法只接受 Content-Type 是 application/json 的请求。
 * 也就是说如果一个请求它的 URL(http://127.0.0.1:8080/Spring4MVC/rest/j) 跟 RequestMethod(POST) 都能匹配上，
 * 但是 Content-Type 不是 application/json，那么这个方法也不会处理这个请求。
 * (Content-Type 标识客户端提交给服务端的数据格式，比如 Content-Type = application/json，意味着客户端告知服务端，我将以 json 格式向你发送数据)
 * 
 * 转换 json 需要引入 jackson-annotations.jar , jackson-core.jar , jackson-databind.jar
 * 转换 xml 需要在类以及属性的 set 方法上加 @XmlRootElement()
 *
 */
@RestController
@RequestMapping({"/rest"})
public class RestfulController {
	
	private String localPath = "http://127.0.0.1:8080/Spring4MVC/rest/";

	/*
	 * @ResponseBody 表示以资源形式返回 Spittle。此处不加 @ResponseBody 也可以，因为类被 @RestController 注释了。
	 * 这里将以 json 方式返回。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/j
	 */
	@RequestMapping(value="/j", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Spittle json(@RequestParam(value="number", defaultValue="7") int number) {
		System.out.println("Number:" + number);
		return new Spittle(9L, "info", new Date(), 3.9);
	}
	
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/j?number=101
	@RequestMapping(value="/j", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Spittle jsonParameter(@RequestBody Spittle spittle, @RequestParam(value="number") int number) {
		System.out.println("Spittle:" + spittle);
		return spittle;
	}
	
	/*
	 * 将返回 xml 格式的数据，Book 需要使用 @XmlRootElement @XmlElement 等标签注释，才能被 xml 化返回。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/x
	 */
	@RequestMapping(value="/x", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody Book xml(@RequestParam(value="number", defaultValue="7") int number) {
		System.out.println("Number:" + number);
		Book book = new Book();
		book.setId(101L);
		book.setName("name");
		book.setDate(new Date());
		book.setPrice(3.9);
		return book;
	}
	
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/x?number=101
	@RequestMapping(value="/x", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public Book xmlParameter(@RequestBody Book book, @RequestParam(value="number") int number) {
		System.out.println("Book:" + book);
		return book;
	}
	
	/*
	 * 使用 ResponseEntity 返回携带状态 header 信息的资源。ResponseEntity 包含了 @ResponseBody 语义，所以不用再加 @ResponseBody 注解。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/entity?number=100
	 */
	@RequestMapping(value="/entity", method = RequestMethod.POST)
	public ResponseEntity<Spittle> entity(@RequestParam(value="number") int number) {
		System.out.println("RestfulController.entity Number:" + number);
		Spittle spittle = new Spittle(9L, "info", new Date(), 3.9);
		HttpStatus status = number > 10 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(spittle, status);
	}
	
	/*
	 * 在 spittr.web.HandleException 中处理异常，并携带 header 信息。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/exception
	 */
	@RequestMapping(value="/exception", method = RequestMethod.GET)
	public Spittle exception() throws ExceptionThree {
		throw new ExceptionThree();
	}
	
	/*
	 * 返回资源，并携带 header 信息，通知客户端，资源已经创建。@ResponseStatus(HttpStatus.CREATED) 标签会添加 header 信息。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/created
	 */
	@RequestMapping(value="/created", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public Spittle restCreated() {
		return new Spittle();
	}
	
	/*
	 * 返回资源，并携带 header 信息，通知客户端，资源已经创建，并通过 local 告知资源位置。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/location
	 */
	@RequestMapping(value="/location", method = RequestMethod.GET)
	public ResponseEntity<Spittle> restLocation(UriComponentsBuilder ucb) {
		HttpHeaders headers = new HttpHeaders();
		URI locationURI = ucb.path("/rest/j").build().toUri();
		headers.setLocation(locationURI);
		return new ResponseEntity<>(new Spittle(), headers, HttpStatus.CREATED);
	}
	
	/*
	 * 通过 RestTemplate 调用其他资源服务器获取资源数据。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/get/object
	 */
	@RequestMapping(value="/get/object", method = RequestMethod.GET)
	public ResponseEntity<Spittle> restGetObject() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("parameter", "j");
		Spittle spittle = rest.getForObject(localPath + "{parameter}", Spittle.class, params);
		System.out.println("Spittle:" + spittle);
		return new ResponseEntity<>(spittle, HttpStatus.CREATED);
	}
	
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/get/entity
	@RequestMapping(value="/get/entity", method = RequestMethod.GET)
	public Spittle restGetEntity() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("parameter", "j");
		ResponseEntity<Spittle> entity = rest.getForEntity(localPath + "{parameter}", Spittle.class, params);
		System.out.println("ResponseEntity<Spittle>:" + entity);
		return entity.getBody();
	}
	
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/put
	@RequestMapping(value="/put", method = RequestMethod.GET)
	public void restPut() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("info", "Detail info");
		rest.put(localPath + "test/put/{info}", new Spittle(9L, "info", new Date(), 3.9), params);
		System.out.println("RestfulController.restPut: Finish.");
	}
	@RequestMapping(value="/test/put/{info}", method = RequestMethod.PUT)
	public Spittle put(@RequestBody Spittle spittle, @PathVariable("info") String info) {
		System.out.println("RestfulController.put:" + info);
		System.out.println("RestfulController.put:" + spittle);
		return spittle;
	}
	
	/*
	 * GET DELETE 请求是没有主体的，也就是说不能像 POST PUT 那样给服务端发送数据，
	 * 所以即便 rest.delete(url, data, parameter); 设置了向 url  发送数据 data，但是实际是没有发过去的。
	 * 此时如果 @RequestBody(required = true) 那么就会报错 Required request body is missing。
	 * 可以将 @RequestBody(required = false) 来解决。
	 * 
	 * 向 GET DELETE 传递数据可以通过 @RequestParam 或者 @PathVariable 来传递
	 * 
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/delete
	 */
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public void restDelete() {
		new RestTemplate().delete(localPath + "test/delete/{info}?number=7", "Detail info");
		System.out.println("RestfulController.restDelete: Finish.");
	}
	@RequestMapping(value="/test/delete/{info}", method = RequestMethod.DELETE)
	public void delete(@RequestBody(required = false) Spittle spittle,
			@PathVariable("info") String info, @RequestParam(value="number", defaultValue="7") int number) {
		System.out.println("RestfulController.delete:" + spittle + " | number=" + number + " | info=" + info);
	}
	
	// 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/post/object
	@RequestMapping(value="/post/object", method = RequestMethod.GET)
	public Spittle restPostObject() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("path", "j");
		/*
		 * 设置参数有几种不同方式：
		 * rest.postForObject(localPath + "{path}", new Spittle(9L, "info", new Date(), 3.9), "j");
		 * rest.postForObject(localPath + "j", new Spittle(9L, "info", new Date(), 3.9));
		 */
		Spittle spittle = rest.postForObject(localPath + "{path}?number=7", new Spittle(9L, "info", new Date(), 3.9), Spittle.class, params);
		System.out.println("Spittle:" + spittle);
		return spittle;
	}
	
	/*
	 * 如果 rest.postForEntity() 返回的状态码不是 200 就会报错，抛异常。
	 * 所以参数如果设置成 {path}?number=3 就会报错。
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/post/entity
	 */
	@RequestMapping(value="/post/entity", method = RequestMethod.GET)
	public ResponseEntity<Spittle> restPostEntity() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("path", "entity");
		ResponseEntity<Spittle> entity = rest.postForEntity(localPath + "{path}?number=11",
				new Spittle(9L, "info", new Date(), 3.9), Spittle.class, params);
		System.out.println("Entity:" + entity);
		return entity;
	}
	
	/*
	 * rest.exchange() 功能强大，可以用来替换其他几个方法。
	 * 这里设置了请求的 header，如果不设置会有一个默认的 header：Accept 是 xml 加 json，Connection 是 keep-alive
	 * 访问路径 http://127.0.0.1:8080/Spring4MVC/rest/exchange
	 */
	@RequestMapping(value="/exchange", method = RequestMethod.GET)
	public ResponseEntity<Spittle> restExchange() {
		RestTemplate rest = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("path", "entity");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> entity = new HttpEntity<>(new Spittle(9L, "info", new Date(), 3.9), headers);
		ResponseEntity<Spittle> result = rest.exchange(localPath + "{path}?number=11", HttpMethod.POST,
				entity, Spittle.class, params);
		System.out.println("Entity:" + result);
		return result;
	}
}
