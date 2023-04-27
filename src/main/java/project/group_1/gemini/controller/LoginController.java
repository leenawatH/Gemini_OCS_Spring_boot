package project.group_1.gemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import project.group_1.gemini.model.User;
import project.group_1.gemini.model.UserRepo;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepo userRepo;

	RestTemplate restTemplate=new RestTemplate();
	
	@RequestMapping("/")
	public String checkMVC() {
		return "login";
	}
	
	@RequestMapping("/login")
	public String loginHomePage(@RequestParam("userName") String userName,
			@RequestParam("password") String password,Model model) {
		User u = null;
		try {
			u=userRepo.findByName(userName);
		}catch (Exception e) {
			System.out.println("User not found !!!");
		}
		if(u!=null) {
			System.out.print(u.getRole());
			if(u.getRole().equals("Astronomer")){
				return "redirect:/Astrodashboard";
			}else if(u.getRole().equals("Science Observer")){
				return "redirect:/SciObdashboard";
			}else if(u.getRole().equals("Admin")){
				return "redirect:/getAllschedule";
			}else{
				model.addAttribute("UserName", userName);
				return "homePage";
			}
			
		}
		model.addAttribute("error","User Not Found");
		System.out.println("ERROR");
		return "login";
	}

}
