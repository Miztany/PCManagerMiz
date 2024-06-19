package almond.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import almond.entity.User;

public interface UserRepository extends JpaRepository<User, String>  {
	
	public List<User> findByEmployeeNum(String employeeNum);
	
	public List<User> findByEmployeeNumAndDeleteFlagFalse(String employeeNum);
	
	public List<User> findByDeleteFlagFalse();

}
