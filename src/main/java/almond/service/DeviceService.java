package almond.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almond.entity.Device;
import almond.entity.History;
import almond.form.DeviceForm;
import almond.repository.DeviceRepository;
import almond.repository.HistoryRepository;

@Service
public class DeviceService {
	
	@Autowired
	DeviceRepository deviceRepository;
	
	@Autowired
	HistoryRepository historyRepository;

	public List<Device> findByDeleteFlagFalse() {
		return deviceRepository.findByDeleteFlagFalse();
	}

	public List<Device> findByAny(String query) {
		return deviceRepository.findByAny(query);
	}

	public void edit(DeviceForm deviceForm) {
		Device d = new Device();
		
		d.setAssetNum(deviceForm.getAsset_num());
		d.setCapacity(deviceForm.getCapacity());
		d.setDeleteFlag(false);
		d.setEndDate(Date.valueOf(deviceForm.getEnd_date()));
		d.setFailure(deviceForm.getFailure());
		d.setGraphicsBoard(deviceForm.getGraphics_board());
		d.setMaker(deviceForm.getMaker());
		d.setMemory(deviceForm.getMemory());
		d.setOperatingSystem(deviceForm.getOperating_system());
		d.setRegisterDate(Date.valueOf(deviceForm.getRegister_date()));
		d.setRemarks(deviceForm.getRemarks());
		d.setStartDate(Date.valueOf(deviceForm.getStart_date()));
		d.setStorageLocation(deviceForm.getStorage_location());
		d.setUpdateDate(Date.valueOf(deviceForm.getUpdate_date()));
		
		deviceRepository.save(d);
		
	}

	public List<Device> findByAssetNum(String assetNum) {
		return deviceRepository.findByAssetNum(assetNum);
	}

	public List<History> findHistoryFive(Device device) {
		List<History> lh = historyRepository.findByDeviceOrderByReturnDateDesc(device);
		if(lh.size() != 0) {
			lh = lh.subList(0, Math.min(5, lh.size()));
		}
		return lh;
	}

	public List<History> findByDeviceOrderByReturnDateDesc(Device device) {
		return historyRepository.findByDeviceOrderByReturnDateDesc(device);
	}

	public void delete(String assetNum) {
		Device d = deviceRepository.findByAssetNum(assetNum).get(0);
		d.setDeleteFlag(true);
		deviceRepository.save(d);		
	}

}

