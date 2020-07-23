package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

import spittr.domain.Spittle;
import spittr.repository.SpittrRepository;

@Controller
@RequestMapping({"/spittles"})
public class SpittleController {

	private SpittrRepository spittrRepository;

	@Autowired
	public SpittleController(SpittrRepository spittrRepository) {
		this.spittrRepository = spittrRepository;
	}

	// http://127.0.0.1:8080/Spring4MVC/spittles
	@RequestMapping(value="/mode", method=RequestMethod.GET)
	public String spittles(Model model) {
		/*
		 * Model 实际上是一个 map 它会以 key - value 方式存储值，如果没有指定 key，它会根据对象的值和类型进行推断。
		 * 此处 spittrRepository.findSpittles(2) 返回的是 List<Spittle> 那么 key 会被认为是 spittleList。
		 * 这个时候数据已经放入到 model 中了，如果视图是 JSP 的话，模型数据会做为请求属性放到 request 中。
		 */
		model.addAttribute(spittrRepository.findSpittles(2));
		model.addAttribute("bi", "value");
		return "spittles";
	}

	// Model 也可以是 Map
	@RequestMapping(method=RequestMethod.POST)
	public String spittlesMap(Map<String, Object> model) {
		model.put("spittles", spittrRepository.findSpittles(2));
		model.put("bi", "value");
		return "spittles";
	}

	// Model 也可以是 Map
	@RequestMapping(method=RequestMethod.GET)
	public List<Spittle> spittlesOptions() {
		/*
		 * 此处没有返回视图，也没有设置 model，这种情况下 Spring 会自己做推断。
		 * 1 推断视图：由于访问的路径是 /spittles 因此视图名将会是 spittles 最终对应 /WEB-INF/views/spittles.jsp
		 * 2 推断模型：return 中的值会作为 value，key 值推断同上，即 spittleList
		 */
		return spittrRepository.findSpittles(2);
	}
}
