package almond.back;

import java.util.List;

import almond.entity.Device;
import almond.entity.History;
import lombok.Data;

@Data
public class DeviceBack {
	private Device device;
	private List<History> listHistory;
}
