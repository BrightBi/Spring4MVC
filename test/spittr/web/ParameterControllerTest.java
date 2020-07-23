package spittr.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Date;

public class ParameterControllerTest {

	@Test
	public void testRequestParam() throws Exception {
		ParameterController controller = new ParameterController();
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 对 "/parameter?name=Mingliang&number=9" 执行 get 请求,并预期得到 info 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/parameter?name=Mingliang&number=9"))
		.andExpect(MockMvcResultMatchers.view().name("info"));
	}
	
	@Test
	public void testPathParam() throws Exception {
		ParameterController controller = new ParameterController();
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 对 "/parameter/path/1001" 执行 get 请求,并预期得到 info 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/parameter/path/1001"))
		.andExpect(MockMvcResultMatchers.view().name("info"));
	}
	
	@Test
	public void testObjectParam() throws Exception {
		ParameterController controller = new ParameterController();
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// 对 "/parameter/object" 执行 post 请求,并预期得到重定向的 /redirct/info 视图
		mokeMvc.perform(MockMvcRequestBuilders.post("/parameter/object")
				.param("id", "1001")
				.param("message", "message")
				.param("time", new Date().toString())
				.param("latitude", "3.9"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/parameter/here"));
	}
	
	@Ignore
	@Test
	public void testValidParam() throws Exception {
		ParameterController controller = new ParameterController();
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// 对 "/parameter/valid" 执行 post 请求,并预期得到 error 视图
		mokeMvc.perform(MockMvcRequestBuilders.post("/parameter/valid"))
		.andExpect(MockMvcResultMatchers.view().name("error"));
	}
}
