package almond.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import almond.entity.Device;
import almond.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {

	List<History> findByDeviceOrderByReturnDateDesc(Device d);

}
