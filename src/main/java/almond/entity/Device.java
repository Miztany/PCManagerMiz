package almond.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="MST_DEVICE")
@Data
public class Device {
	@Id
	@Column(name = "asset_num")
	private String assetNum;
	@Column(name = "maker")
	private String maker;
	@Column(name = "operating_system")
	private String operatingSystem;
	@Column(name = "memory")
	private int memory;
	@Column(name = "capacity")
	private String capacity;
	@Column(name = "graphics_board")
	private Boolean graphicsBoard;
	@Column(name = "storage_location")
	private String storageLocation;
	@Column(name = "failure")
	private Boolean failure;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "register_date")
	private Date registerDate;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "delete_flag")
	private Boolean deleteFlag;

}
