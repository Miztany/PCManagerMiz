package almond.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="TRN_HISTORY")
@Data
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "histories")
	private int historyies;
	@ManyToOne
	@JoinColumn(name = "asset_num")
	private Device device;
	@Column(name = "loan_date")
	private Date loanDate;
	@Column(name = "return_date")
	private Date returnDate;
	@ManyToOne
	@JoinColumn(name = "employee_num")
	private User user;
	

}
