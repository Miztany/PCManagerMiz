package almond.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import almond.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, String>  {

	@Query("select r from Rental r where r.device.failure = false and r.device.deleteFlag = false")
	public List<Rental> findByFailureFalse();
	
	public List<Rental> findByAssetNum(String assetNum);

}
