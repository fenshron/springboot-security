package com.us.example.controller;

import com.us.example.domain.Msg;
import com.us.example.domain.SysUser;
import com.us.example.service.test.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangyibo on 17/1/18.
 */
@Controller
public class HomeController {
	
	@Autowired
	private TestService testService;

    @RequestMapping("/")
    public String index(Model model){
        Msg msg =  new Msg("测试标题","测试内容","欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
        model.addAttribute("msg", msg);
        return "home";
    }

    @RequestMapping("/login")
    public  String login(){
        return "login";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
		String username = userDetails.getUsername();
		SysUser userByName = testService.getUserByName(username);
		System.out.println(userByName.getPassword()+"-----------");
        return "hello "+username;
    }
}
