package almond.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TRN_RENTAL")
public class Rental {
	@Id
	@Column(name="asset_num")
	private String assetNum;
	@OneToOne
	@JoinColumn(name = "asset_num")
	private Device device;
	@Column(name = "empt")
	private Boolean empty;
	@ManyToOne
	@JoinColumn(name = "employee_num")
	private User user;
	@Column(name = "loan_date")
	private Date loanDate;
	@Column(name = "inventory_date")
	private Date inventoryDate;
	@Column(name = "remarks")
	private String remarks;

}
