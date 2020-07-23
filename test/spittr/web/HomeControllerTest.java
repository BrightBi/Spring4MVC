package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HomeControllerTest {

	@Test
	public void testHome() throws Exception {
		HomeController homeController = new HomeController();
		// 搭建 MockMvc
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		// 对 "/home/home" 执行 get 请求,并预期得到 home 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/home/detail")).andExpect(MockMvcResultMatchers.view().name("info"));
	}
	
	@Test
	public void testHomePage() throws Exception {
		HomeController homeController = new HomeController();
		// 搭建 MockMvc
		MockMvc mokeMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		// 对 "/homePage/home" 执行 get 请求,并预期得到 home 视图
		mokeMvc.perform(MockMvcRequestBuilders.get("/homePage/detail")).andExpect(MockMvcResultMatchers.view().name("info"));
	}
}
