package almond.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almond.back.RentalBack;
import almond.entity.Device;
import almond.entity.History;
import almond.entity.Rental;
import almond.entity.User;
import almond.form.RemarksForm;
import almond.form.RentalForm;
import almond.form.ReturnForm;
import almond.repository.DeviceRepository;
import almond.repository.HistoryRepository;
import almond.repository.RentalRepository;
import almond.repository.UserRepository;

@Service
public class RentalService {

	@Autowired
	RentalRepository rentalRepository;

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HistoryRepository historyRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//	一覧取得
	public List<RentalBack> findByFailureFlase() {
		List<RentalBack> listRentalBack = new ArrayList<RentalBack>();
		List<Rental> listRental = rentalRepository.findByFailureFalse();

		for (Rental r : listRental) {
			listRentalBack.add(ConvRentalToRentalBack(r));
		}

		return listRentalBack;
	}

//	サマリの取得
	public List<Integer> getSummary() {
		List<Rental> listRental = rentalRepository.findByFailureFalse();
		List<Integer> EmptyNotEmpty = new ArrayList<Integer>();
		EmptyNotEmpty.add(0);
		EmptyNotEmpty.add(0);

		for (Rental r : listRental) {
			if (r.getEmpty()) {
				EmptyNotEmpty.set(0, EmptyNotEmpty.get(0) + 1);
			} else {
				EmptyNotEmpty.set(1, EmptyNotEmpty.get(1) + 1);
			}
		}

		return EmptyNotEmpty;
	}

//	詳細の取得
	public List<RentalBack> findByAssetNum(String assetNum) {
		List<Rental> listRental = rentalRepository.findByAssetNum(assetNum);
		List<RentalBack> listRentalBack = new ArrayList<RentalBack>();

		for (Rental r : listRental) {
			listRentalBack.add(ConvRentalToRentalBack(r));
		}

		return listRentalBack;
	}

//	ReturnからReturnBackへの変換
	private RentalBack ConvRentalToRentalBack(Rental r) {
		RentalBack rb = new RentalBack();
		rb.setAssetNum(r.getDevice().getAssetNum());
		rb.setInventoryDate(sdf.format(r.getInventoryDate()));
		rb.setMaker(r.getDevice().getMaker());
		rb.setOperatingSystem(r.getDevice().getOperatingSystem());
		rb.setRemarks(r.getRemarks());
		rb.setStorageLocation(r.getDevice().getStorageLocation());

		if (r.getEmpty()) {
//			貸出可の場合
			rb.setEmpty("可");
		} else {
//			貸出中の場合
			rb.setEmployeeNum(r.getUser().getEmployeeNum());
			rb.setEmpty("不可");
			rb.setLoanDate(sdf.format(r.getLoanDate()));
			rb.setName(r.getUser().getName());
//			返却予定日の計算
			Calendar c = Calendar.getInstance();
			c.setTime(r.getLoanDate());
			c.add(Calendar.MONTH, 3);
			rb.setExpReturnDate(sdf.format(c.getTime()));
		}

		return rb;

	}

//	社員名の取得
	public List<User> userDetail(String employeeNum) {
		return userRepository.findByEmployeeNumAndDeleteFlagFalse(employeeNum);
	}

//	レンタル時の処理
	public void rental(RentalForm rentalForm) {
		Rental r = rentalRepository.findByAssetNum(rentalForm.getAsset_num()).get(0);
		User u = userRepository.findByEmployeeNum(rentalForm.getEmployee_num()).get(0);
		r.setEmpty(false);
		r.setUser(u);
		r.setLoanDate(Date.valueOf(rentalForm.getLoan_date()));
		rentalRepository.save(r);
	}

//	返却時の処理
	public void back(ReturnForm returnForm) {
		Rental r = rentalRepository.findByAssetNum(returnForm.getAsset_num()).get(0);
		History h = new History();

		h.setDevice(r.getDevice());
		h.setLoanDate(r.getLoanDate());
		h.setUser(r.getUser());
		h.setReturnDate(Date.valueOf(returnForm.getReturn_date()));
		historyRepository.save(h);

		r.setEmpty(true);
		r.setUser(null);
		r.setLoanDate(null);
		rentalRepository.save(r);

	}

//	備考／棚卸時の処理
	public void setRemarks(RemarksForm remarksForm) {
		Rental r = rentalRepository.findByAssetNum(remarksForm.getAsset_num()).get(0);
		r.setInventoryDate(Date.valueOf(remarksForm.getInventory_date()));
		r.setRemarks(remarksForm.getRemarks());
		rentalRepository.save(r);

	}

	public List<Device> findUsableDevices() {
		List<Rental> lr = rentalRepository.findAll();
		List<Device> ld = deviceRepository.findAll();
		List<Device> usableld = new ArrayList<Device>();

		dLoop: for (Device d : ld) {
			for (Rental r : lr) {
				if (r.getAssetNum().equals(d.getAssetNum())) continue dLoop;
			}
			usableld.add(d);
		}
		return usableld;

	}

	public void addDevice(RemarksForm remarksForm) {
		Rental r = new Rental();
		Device d = deviceRepository.findByAssetNum(remarksForm.getAsset_num()).get(0);		
		r.setAssetNum(d.getAssetNum());
		r.setEmpty(true);
		r.setInventoryDate(Date.valueOf(remarksForm.getInventory_date()));
		r.setRemarks(remarksForm.getRemarks());
		rentalRepository.save(r);		
	}

}
