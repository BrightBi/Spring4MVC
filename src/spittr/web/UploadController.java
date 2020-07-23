package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@Controller
@RequestMapping({ "/upload" })
public class UploadController {

	/*
	 * 请求路径 http://127.0.0.1:8080/Spring4MVC/upload/standard
	 * 上传文件需要配置 StandardServletMultipartResolver bean 和设置 MultipartConfigElement
	 */
	@RequestMapping(value = "/standard", method = RequestMethod.POST)
	public String uploadByStandard(@RequestPart("file") MultipartFile file, @RequestParam("other") String other,
			HttpServletRequest request) throws IllegalStateException, IOException {
		System.out.println("other:" + other);
		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator
					+ filename;
			System.out.println("path:" + path);
			file.transferTo(new File(path));
		}
		return "info";
	}
	/*
	 * http://127.0.0.1:8080/Spring4MVC/upload/part
	 * 使用 Part 来上传文件可以不配置 StandardServletMultipartResolver bean，
	 * 但是 MultipartConfigElement 还是需要配置的
	 */
	@RequestMapping(value = "/part", method = RequestMethod.POST)
	public String uploadByPart(@RequestPart("file") Part file, @RequestParam("other") String other,
			HttpServletRequest request) throws IOException {
		System.out.println("other:" + other);
		if (file != null) {
			String filename = file.getSubmittedFileName();
			String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator
					+ filename;
			System.out.println("path:" + path);
			file.write(path);
		}
		return "info";
	}
}
