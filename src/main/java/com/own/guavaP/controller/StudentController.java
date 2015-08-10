package com.own.guavaP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.own.guavaP.factory.MyCacheFactory;
import com.own.guavaP.service.StudentService;
@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;//这里用bean
	
	@RequestMapping(value="/message")
   public String getMessageById(Model model){
		String msg=studentService.getMsg(1);
		model.addAttribute("msg", msg);
	   return "stumessage";
   }
	
	@RequestMapping(value="/factory")
	public String getMsgFacById(Model model){
		String msg=studentService.factory(1);
		model.addAttribute("msg", msg);
		return "stumessage";
	}
	@RequestMapping(value="/spec")
	public String getMsgSpecById(Model model){
		String msg=studentService.spec(1);
		model.addAttribute("msg", msg);
		return "stumessage";
	}
	
	
}
