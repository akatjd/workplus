package com.kjc.workplus.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
	@RequestMapping(value="/")
	public String main() {
		return "/main";
	}
}
