package almond.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almond.entity.User;
import almond.form.UserForm;
import almond.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public List<User> findByDeleteFlagFalse() {
		return userRepository.findByDeleteFlagFalse();
	}

	public void edit(UserForm userForm) {
		User u = new User();
		u.setDeleteFlag(false);
		String accountLevel = "";
		switch (userForm.getAccount_level()) {
			case 1:
				accountLevel = "管理者";
				break;
			case 2:
				accountLevel = "利用者";
				break;
		}
		u.setAccountLevel(accountLevel);
		u.setAge(userForm.getAge());
		u.setDepartment(userForm.getDepartment());
		u.setEmployeeNum(userForm.getEmployee_num());
		u.setGender(userForm.getGender());
		u.setMailAddress(userForm.getMail_address());
		u.setName(userForm.getName());
		u.setNameKana(userForm.getName_kana());
		u.setPosition(userForm.getPosition());
		u.setRegisterDate(Date.valueOf(userForm.getRegister_date()));
		u.setRetireDate(userForm.getRetire_date() == null ? null : Date.valueOf(userForm.getRetire_date()));
		u.setTelNum(userForm.getTel_num());
		u.setUpdateDate(Date.valueOf(userForm.getUpdate_date()));

		userRepository.save(u);
	}

	public List<User> findByEmployeeNum(String employeeNum) {
		return userRepository.findByEmployeeNum(employeeNum);
	}

	public void delete(String employeeNum) {
		User u = userRepository.findByEmployeeNum(employeeNum).get(0);
		u.setDeleteFlag(true);
		userRepository.save(u);
	}

}
