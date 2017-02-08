package com.online.adwork.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.online.adwork.services.UserServiceImpl;
import com.online.adwork.viewobject.UserRegistration;

@Controller
public class Adwork 
{
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Secured("ROLE_USER")
	@GetMapping("/registartionForm")
	public String getRegistartionForm(ModelMap model)
	{
		UserRegistration userRegistration = new UserRegistration();
		model.addAttribute("userRegistration", userRegistration);
		return "RegistrationForm";
	}
	
	@GetMapping("/logins")
	public String getLoginPage()
	{
		System.out.println("Called");
		return "login";
	}
	
	@GetMapping("/hello")
	public String getSuccess()
	{
		return "success";
	}
	
	@PostMapping("/saveRegistartionDetails")
	public String saveRegistrationDetails(@Valid UserRegistration userRegistration,BindingResult result,ModelMap model)
	{
		System.out.println("Controller Layer");
		model.addAttribute("userRegistration", userRegistration);
		if(result.hasErrors())
			//return "redirect:/registartionForm" request redirect all message will be removed from request attribute;
			return "RegistrationForm";
		else
		{
			model.addAttribute("success", "Dear "+userRegistration.getFirstName()+" DOB "+userRegistration.getDateOfBirth());
			boolean flag = userServiceImpl.saveUserRegistration(userRegistration);
			return "success";
		}
		
	}
}
