package pl.uplukaszp.exchangeOffice.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.dto.UserDTO;
import pl.uplukaszp.exchangeOffice.service.UserService;

@Controller
@SessionAttributes
@AllArgsConstructor
public class UserController {
	private static final String USER_EXIST_ERROR_CODE = "login.exist";
	private static final String USER_EXIST_MESSAGE = "User with this login exist";
	private static final String LOGIN_FORM_NAME = "login";
	private static final String REGISTER_FORM_NAME = "register";
	
	private UserService userService;

	@GetMapping("/login")
	public String getLoginForm() {
		return LOGIN_FORM_NAME;
	}

	@GetMapping("/register")
	public String getRegisterForm(Model model) {
		initRegisterForm(model);
		return REGISTER_FORM_NAME;
	}

	private void initRegisterForm(Model model) {
		model.addAttribute("user", new UserDTO());
	}

	@PostMapping("/register")
	public String processRegisterForm(@ModelAttribute(name = "user") @Valid UserDTO user, BindingResult errors) {
		if (userExist(user)) {
			errors.rejectValue(LOGIN_FORM_NAME, USER_EXIST_ERROR_CODE, USER_EXIST_MESSAGE);
		}
		if (errors.hasErrors()) {
			return REGISTER_FORM_NAME;
		}
		userService.registerNewUser(user);
		return "redirect:/login";
	}

	private boolean userExist(UserDTO user) {
		return userService.findByLogin(user.getLogin()) != null;
	}
}
