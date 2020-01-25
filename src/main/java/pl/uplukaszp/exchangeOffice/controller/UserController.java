package pl.uplukaszp.exchangeOffice.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.dto.UserDTO;
import pl.uplukaszp.exchangeOffice.service.UserService;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class UserController {
	UserService userService;
	@GetMapping
	public String getRegisterForm(Model model) {
		model.addAttribute("user",new UserDTO());
		return "register";
	}
	
	@PostMapping
	public String registerUser(@Valid UserDTO user,BindingResult errors) {
		System.out.println(user);
		if(errors.hasErrors()) {
			System.out.println("error");
			return "register";
		}
		userService.registerUser(user);
		return "redirect:/login";
	}
}
