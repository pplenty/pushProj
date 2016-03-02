package com.pushman.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {
	
	
	@RequestMapping(value="/test", method = RequestMethod.POST)
	@ResponseBody
	public Object testJS(
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {

		System.out.println("tset");
		
		Map<String, String> responseData = new HashMap<String, String>();
		responseData.put("fromDate", request.getParameter("fromDate"));
		
		
		return responseData;
	}
}
