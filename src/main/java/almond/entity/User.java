package almond.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="MST_USER")
@Data
public class User {
	@Id
	@Column(name = "employee_num")
	private String employeeNum;
	@Column(name = "name")
	private String name;
	@Column(name = "name_kana")
	private String nameKana;
	@Column(name = "department")
	private String department;
	@Column(name = "tel_num")
	private String telNum;
	@Column(name = "mail_address")
	private String mailAddress;
	@Column(name = "age")
	private int age;
	@Column(name = "gender")
	private int gender;
	@Column(name = "position")
	private String position;
	@Column(name = "account_level")
	private String accountLevel;
	@Column(name = "retire_date")
	private Date retireDate;
	@Column(name = "register_date")
	private Date registerDate;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "delete_Flag")
	private Boolean deleteFlag;
	

}
