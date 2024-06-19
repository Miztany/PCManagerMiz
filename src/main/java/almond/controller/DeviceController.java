package almond.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import almond.back.DeviceBack;
import almond.back.Status;
import almond.entity.Device;
import almond.entity.History;
import almond.form.DeviceForm;
import almond.form.SearchForm;
import almond.service.DeviceService;

@Controller
public class DeviceController {

	@Autowired
	DeviceService deviceService;

	@GetMapping("/device")
	public String firstload(Model model) {
		model.addAttribute("listDevice", deviceService.findByDeleteFlagFalse());
		return "device";
	}

	@GetMapping("/device/search")
	public String search(@Validated @ModelAttribute("search") SearchForm searchForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/device";
		} else {
			model.addAttribute("listDevice", deviceService.findByAny(searchForm.getQuery()));
			return "device";
		}

	}

	@PostMapping("/device/edit")
	@ResponseBody
	public Status edit(@Validated @ModelAttribute("edit") DeviceForm deviceForm, BindingResult bindingResult) {
		Status s = new Status();
		s.setResult(true);

		if (bindingResult.hasErrors()) {
			s.setMessage("入力に誤りがあります");
			s.setResult(false);
		} else {
			deviceService.edit(deviceForm);
		}

		return s;
	}

	@GetMapping("/device/detail")
	@ResponseBody
	public DeviceBack detail(@RequestParam String assetNum) {
		List<Device> ld = deviceService.findByAssetNum(assetNum);
		List<History> lh = deviceService.findHistoryFive(ld.get(0));
		DeviceBack db = new DeviceBack();
		db.setDevice(ld.get(0));
		db.setListHistory(lh);
		return db;
	}

	@GetMapping("/device/moreHistory")
	@ResponseBody
	public List<History> moreHistory(@RequestParam String assetNum) {
		List<Device> ld = deviceService.findByAssetNum(assetNum);
		return deviceService.findByDeviceOrderByReturnDateDesc(ld.get(0));
	}

	@GetMapping("/device/delete")
	@ResponseBody
	public void delete(@RequestParam String assetNum) {
		deviceService.delete(assetNum);
	}

}
