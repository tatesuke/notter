package com.tatesuke.notter.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

}