package almond.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceForm {
	@NotBlank
	@Size(min = 1, max = 10)
	private String asset_num;
	@NotBlank
	@Size(min = 1, max = 20)
	private String maker;
	@NotBlank
	@Size(min = 1, max = 20)
	private String operating_system;
	@NotNull
	private Integer memory;
	@NotBlank
	@Size(min = 1, max = 5)
	private String capacity;
	@NotNull
	private Boolean graphics_board;
	@NotBlank
	@Size(min = 1, max = 30)
	private String storage_location;
	@NotNull
	private Boolean failure;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start_date;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end_date;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate register_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate update_date;
	private String remarks;
}
