package almond.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import almond.entity.Device;

public interface DeviceRepository  extends JpaRepository<Device, String>  {
	
	public List<Device> findByAssetNum(String assetNum);

	public List<Device> findByDeleteFlagFalse();

	@Query("select d from Device d"
			+ " where (d.assetNum like %?1%"
			+ " or d.storageLocation like %?1%)"
			+ " and d.deleteFlag = false")
	public List<Device> findByAny(String query);

}
