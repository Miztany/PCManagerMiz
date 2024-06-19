let editModal;
let deviceModal;

let reqDetail = new XMLHttpRequest();
let reqEdit = new XMLHttpRequest();
let reqMoreHistory = new XMLHttpRequest();
let reqDelete = new XMLHttpRequest();


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


//新規作成ボタンで呼び出される
function showModalAdd() {
	//	フォームをクリアする
	inputList = document.getElementsByTagName('input')
	for (let i = 0; i < inputList.length; i++) {
		inputList[i].value = null;
	}
	document.getElementById('form-graphics_board').value = true;
	document.getElementById('form-failure').value = false;
	document.getElementById('form-asset_num').readOnly = false;

	editModal = new bootstrap.Modal(document.getElementById('editModal'));
	editModal.show();
}

//編集ボタンで呼び出される
function showModalEdit() {
	document.getElementById('form-asset_num').readOnly = true;

	deviceModal.hide();

	editModal = new bootstrap.Modal(document.getElementById('editModal'));
	editModal.show();
}


//登録／更新ボタンで呼び出される
function editDevice() {
	reqEdit.open('POST', '/device/edit', true);
	reqEdit.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
	reqEdit.send(getFormSerial('form-edit'));

}

reqEdit.onload = function() {
	let json = JSON.parse(reqEdit.responseText);
	if (json['result']) {
		//		モーダルを閉じる
		editModal.hide();
		location.reload();
	} else {
		//		エラーメッセージを表示する
		alert(json['message']);
	}

}


//詳細ボタンで呼び出される
function showDetail(assetNum) {
	//	フォームをクリアする
	inputList = document.getElementsByTagName('input')
	for (let i = 0; i < inputList.length; i++) {
		inputList[i].value = null;
	}

	reqDetail.open('GET', '/device/detail?assetNum=' + assetNum);
	reqDetail.send(null);

}

reqDetail.onload = function() {
	let json = JSON.parse(reqDetail.responseText);
	let listHistory = json['listHistory'];
	let device = json['device'];

	//	html側のidとjson側のkeyの対応
	let idKeyAAry = {
		'modal-asset_num': 'assetNum',
		'modal-maker': 'maker',
		'modal-operating_system': 'operatingSystem',
		'modal-memory': 'memory',
		'modal-capacity': 'capacity',
		//		'modal-graphics_board': 'graphicsBoadrd',
		'modal-storage_location': 'storageLocation',
		//		'modal-failure': 'failure',
		'modal-start_date': 'startDate',
		'modal-end_date': 'endDate',
		'modal-register_date': 'registerDate',
		'modal-update_date': 'updateDate',
		'modal-remarks': 'remarks',
	};

	//	詳細モーダルに値をいれる
	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).innerText = device[key];
	})
	document.getElementById('modal-graphics_board').innerText = device['graphicsBoard'] ? 'あり' : 'なし';
	document.getElementById('modal-failure').innerText = device['failure'] ? '◯' : '';


	//	履歴に値をいれる
	let history = document.getElementById('modal-history');
	while (history.firstChild) {
		history.removeChild(history.firstChild);
	}
	listHistory.forEach(h => {
		let newLi = document.createElement('li');
		newLi.classList.add('list-group-item');
		newLi.innerText = h.loanDate + '～' + h.returnDate + '　' + h.user.name;
		history.appendChild(newLi);
	})

	//	更に表示にリンクを設定
	document.getElementById('linkMoreHistory').classList.remove('d-none');
	document.getElementById('linkMoreHistory').href = `javascript:moreHistory('${device.assetNum}')`;


	//	削除ボタンにアクションを設定
	document.getElementById('button-delete').onclick = deleteDevice.bind(null, device.assetNum);


	//	編集モーダルに値をいれる
	idKeyAAry = {
		'form-asset_num': 'assetNum',
		'form-maker': 'maker',
		'form-operating_system': 'operatingSystem',
		'form-memory': 'memory',
		'form-capacity': 'capacity',
		'form-graphics_board': 'graphicsBoard',
		'form-storage_location': 'storageLocation',
		'form-failure': 'failure',
		'form-start_date': 'startDate',
		'form-end_date': 'endDate',
		'form-register_date': 'registerDate',
		'form-update_date': 'updateDate',
		'form-remarks': 'remarks',
	}

	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).value = device[key];
	})

	// モーダルの表示
	deviceModal = new bootstrap.Modal(document.getElementById('deviceModal'));
	deviceModal.show();
}



//更に表示を押したときに呼び出される
function moreHistory(assetNum) {
	document.getElementById('linkMoreHistory').classList.add('d-none');
	reqMoreHistory.open('GET', '/device/moreHistory?assetNum=' + assetNum);
	reqMoreHistory.send(null);
}

reqMoreHistory.onload = function() {
	let json = JSON.parse(reqMoreHistory.responseText);
	//	履歴に値をいれる
	let history = document.getElementById('modal-history');
	while (history.firstChild) {
		history.removeChild(history.firstChild);
	}
	json.forEach(h => {
		let newLi = document.createElement('li');
		newLi.classList.add('list-group-item');
		newLi.innerText = h.loanDate + '～' + h.returnDate + '　' + h.user.name;
		history.appendChild(newLi);
	})
}


// 削除を押したときに呼び出される
function deleteDevice(assetNum) {
	result = confirm(assetNum + 'のデータを削除します');

	if (result) {
		reqDelete.open('GET', '/device/delete?assetNum=' + assetNum);
		reqDelete.send(null);
	}
}

reqDelete.onload = function(){
	location.reload();
}
