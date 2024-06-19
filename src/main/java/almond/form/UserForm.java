package almond.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {

	@NotBlank
	@Size(min = 1, max = 10)
	private String employee_num;
	@NotBlank
	@Size(min = 1, max = 20)
	private String name;
	@NotBlank
	@Size(min = 1, max = 20)
	private String name_kana;
	@NotBlank
	@Size(min = 1, max = 20)
	private String department;
	@NotBlank
	@Size(min = 1, max = 20)
	private String tel_num;
	@NotBlank
	@Email
	@Size(min = 1, max = 50)
	private String mail_address;
	@NotNull
	private Integer age;
	@NotNull
	private Integer gender;
	@NotBlank
	@Size(min = 1, max = 20)
	private String position;
	@NotNull
	private Integer account_level;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate retire_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate register_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate update_date;

}
