package com.hkha.ea.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
	//@RequestMapping("/hello")
	@RequestMapping(value={"/hello.do", "/common/hello.do"})
	public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		
		model.addAttribute("name", name);
		
		//returns the view name
		return "helloworld";

 }
}
