let reqDetail = new XMLHttpRequest();
let reqRental = new XMLHttpRequest();
let reqUserDetail = new XMLHttpRequest();
let reqReturn = new XMLHttpRequest();
let reqGetRemarks = new XMLHttpRequest();
let reqSetRemarks = new XMLHttpRequest();
let reqUsableDevices = new XMLHttpRequest();
let reqAddDevice = new XMLHttpRequest();


let modal;


//formのidをうけとって中身の文字列をkey=valueの文字列で返す
function getFormSerial(formId) {
	let inputList = document.querySelectorAll(`#${formId} input`);
	let str = ""
	inputList.forEach((e, i) => {
		if (i != 0) {
			str = str + '&';
		}
		str = str + e.name + '=' + e.value;
	})

	inputList = document.querySelectorAll(`#${formId} select`);
	inputList.forEach((e, i) => {
		str = str + '&' + e.name + '=' + e.value;
	})
	return str
}


//詳細を押したときに呼び出される
function showDetail(assetNum) {
	//	フォームをクリアする
	inputList = document.getElementsByTagName('input')
	for (let i = 0; i < inputList.length; i++) {
		inputList[i].value = null;
	}
	reqDetail.open('GET', '/detail?assetNum=' + assetNum);
	reqDetail.send(null);
}

reqDetail.onload = function() {
	let json = JSON.parse(reqDetail.responseText);

	//	html側のidとjson側のkeyの対応
	let idKeyAAry = {
		'modal-asset_num': 'assetNum',
		'modal-maker': 'maker',
		'modal-operating_system': 'operatingSystem',
		'modal-storage_location': 'storageLocation',
		'modal-empty': 'empty',
		'modal-employee_num': 'employeeNum',
		'modal-name': 'name',
		'modal-loan_date': 'loanDate',
		'modal-exp_return_date': 'expReturnDate',
		'modal-inventory_date': 'inventoryDate',
		'modal-remarks': 'remarks',
	};

	//	モーダルに値をいれる
	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).innerText = json[key];
	})

	//	貸出と返却の表示を切り替える
	if (json['empty'] === '可') {
		document.getElementById('form-rental').classList.remove('d-none');
		document.getElementById('button-rental').classList.remove('d-none');
		document.getElementById('form-return').classList.add('d-none');
		document.getElementById('button-return').classList.add('d-none');
		document.getElementById('rental-asset_num').value = json['assetNum'];
	} else if (json['empty'] === '不可') {
		document.getElementById('form-return').classList.remove('d-none');
		document.getElementById('button-return').classList.remove('d-none');
		document.getElementById('form-rental').classList.add('d-none');
		document.getElementById('button-rental').classList.add('d-none');
		document.getElementById('return-asset_num').value = json['assetNum'];
	}

	// モーダルの表示
	modal = new bootstrap.Modal(document.getElementById('rentalModal'));
	modal.show();
}



//貸出を押したときに呼び出される
function rentalDevice() {
	reqUserDetail.open('GET', '/userDetail?employeeNum=' + document.getElementById('rental-employee_num').value);
	reqUserDetail.send(null);
}

reqUserDetail.onload = function() {
	let json = JSON.parse(reqUserDetail.responseText);
	let result;


	if (json['name'] === null | json['name'] === undefined) {
		alert('社員番号が間違っています');
	} else {
		result = confirm(json['name'] + "さんであっていますか");
	}

	if (result) {
		reqRental.open('POST', '/rental', true);
		reqRental.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
		reqRental.send(getFormSerial('form-rental'));
	}

}

reqRental.onload = function() {
	let json = JSON.parse(reqRental.responseText);
	if (json['result']) {
		//		モーダルを閉じる
		modal.hide();
		location.reload();
	} else {
		//		エラーメッセージを表示する
		alert(json['message']);
	}

}


//返却を押したときに呼び出される
function returnDevice() {
	reqReturn.open('POST', '/return', true);
	reqReturn.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
	reqReturn.send(getFormSerial('form-return'));
}

reqReturn.onload = function() {
	let json = JSON.parse(reqReturn.responseText);
	if (json['result']) {
		//		モーダルを閉じる
		modal.hide();
		location.reload();
	} else {
		//		エラーメッセージを表示する
		alert(json['message']);
	}

}


//…ボタンで呼び出される
function showModalRemarks(assetNum) {

	reqGetRemarks.open('GET', '/detail?assetNum=' + assetNum);
	reqGetRemarks.send(null);

}

reqGetRemarks.onload = function() {
	let json = JSON.parse(reqGetRemarks.responseText);
	let idKeyAAry;

	//	リマークモーダルに値をいれる
	idKeyAAry = {
		'form-asset_num': 'assetNum',
		'form-inventory_date': 'inventoryDate',
		'form-remarks': 'remarks',
	}

	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).value = json[key];
	})

	// モーダルの表示
	modal = new bootstrap.Modal(document.getElementById('remarksModal'));
	modal.show();

}


//更新ボタンで呼び出される
function setRemarks() {
	reqSetRemarks.open('POST', '/setRemarks', true);
	reqSetRemarks.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
	reqSetRemarks.send(getFormSerial('form-editRemarks'));

}

reqSetRemarks.onload = function() {
	let json = JSON.parse(reqSetRemarks.responseText);
	if (json['result']) {
		//		モーダルを閉じる
		modal.hide();
		location.reload();
	} else {
		//		エラーメッセージを表示する
		alert(json['message']);
	}

}


//機器追加ボタンで呼び出される
function showModalAdd() {
	reqUsableDevices.open('GET', '/usableDevices');
	reqUsableDevices.send(null);

}

reqUsableDevices.onload = function() {
	let json = JSON.parse(reqUsableDevices.responseText);

	json.forEach(d => {
		let op = document.createElement('option');
		op.value = d.assetNum;
		op.innerText = d.assetNum;
		document.getElementById('formregist-asset_num').appendChild(op);
	})

	modal = new bootstrap.Modal(document.getElementById('addModal'));
	modal.show();

}


//登録ボタンで呼び出される
function registDevice() {
	reqAddDevice.open('POST', '/addDevice', true);
	reqAddDevice.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
	reqAddDevice.send(getFormSerial('form-addDevice'));
}

reqAddDevice.onload = function() {
	let json = JSON.parse(reqAddDevice.responseText);
	if (json['result']) {
		//		モーダルを閉じる
		modal.hide();
		location.reload();
	} else {
		//		エラーメッセージを表示する
		alert(json['message']);
	}

}