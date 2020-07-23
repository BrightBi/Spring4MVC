package spittr.web;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import spittr.domain.Spittle;
import spittr.repository.SpittrRepository;

public class SpittleControllerTest {

	@Test
	public void testSpittles() throws Exception {
		SpittrRepository spittrRepository = Mockito.mock(SpittrRepository.class);
		Mockito.when(spittrRepository.findSpittles(2)).thenReturn(getSpittles(2));
		SpittleController controller = new SpittleController(spittrRepository);
		/*
		 *  搭建 MockMvc，并设置 setSingleView。这样 mock 框架就不用去解析控制器中的视图名了。
		 *  一般情况不需要这么做，此处由于 请求路径跟视图名一样，如果按照默认试图解析规则，MockMvc 无法区分视图路径跟控制器路径，会报错。
		 *  实际路径无关紧要，但要跟 spittr.config.WebConfig 配置保持一致。
		 */
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 对 "/spittles" 执行 get 请求,并预期得到 spittles 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/spittles/mode")).andExpect(MockMvcResultMatchers.view().name("spittles"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("bi"))
		.andExpect(MockMvcResultMatchers.model().attribute("bi", "value"));
	}

	@Test
	public void testSpittlesMap() throws Exception {
		SpittrRepository spittrRepository = Mockito.mock(SpittrRepository.class);
		Mockito.when(spittrRepository.findSpittles(2)).thenReturn(getSpittles(2));
		SpittleController controller = new SpittleController(spittrRepository);
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 对 "/spittles" 执行 get 请求,并预期得到 spittles 视图
		mokeMvc.perform(MockMvcRequestBuilders.post("/spittles")).andExpect(MockMvcResultMatchers.view().name("spittles"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("spittles"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("bi"))
		.andExpect(MockMvcResultMatchers.model().attribute("bi", "value"));
	}

	@Test
	public void testSpittlesOptions() throws Exception {
		SpittrRepository spittrRepository = Mockito.mock(SpittrRepository.class);
		Mockito.when(spittrRepository.findSpittles(2)).thenReturn(getSpittles(2));
		SpittleController controller = new SpittleController(spittrRepository);
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 对 "/spittles" 执行 get 请求,并预期得到 spittles 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/spittles")).andExpect(MockMvcResultMatchers.view().name("spittles"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"));
	}
	
	private List<Spittle> getSpittles(int number) {
		Spittle one = new Spittle();
		one.setId(100L);
		one.setMessage("one message");
		one.setTime(new Date());
		one.setLatitude(3.9);
		Spittle two = new Spittle();
		two.setId(200L);
		two.setMessage("two message");
		two.setTime(new Date());
		two.setLatitude(7.9);
		List<Spittle> spittles = new ArrayList<>();
		spittles.add(one);
		spittles.add(two);
		return spittles;
	}
}
