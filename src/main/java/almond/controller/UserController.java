package almond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import almond.back.Status;
import almond.entity.User;
import almond.form.UserForm;
import almond.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/user")
	public String firstload(Model model) {
		model.addAttribute("listUser", userService.findByDeleteFlagFalse());
		return "user";
	}
	
	@PostMapping("/user/edit")
	@ResponseBody
	public Status edit(@Validated @ModelAttribute("edit") UserForm userForm, BindingResult bindingResult) {
		Status s = new Status();
		s.setResult(true);

		if (bindingResult.hasErrors()) {
			s.setMessage("入力に誤りがあります");
			System.out.println(bindingResult.getAllErrors());
			s.setResult(false);
		} else {
			userService.edit(userForm);
		}

		return s;
	}
	
	@GetMapping("/user/detail")
	@ResponseBody
	public User detail(@RequestParam String employeeNum) {
		return userService.findByEmployeeNum(employeeNum).get(0);
	}
	
	@GetMapping("/user/delete")
	@ResponseBody
	public void delete(@RequestParam String employeeNum) {
		userService.delete(employeeNum);
	}

}
