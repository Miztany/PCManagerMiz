let editModal;
let userModal;

let reqDetail = new XMLHttpRequest();
let reqEdit = new XMLHttpRequest();
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
	document.getElementById('form-gender').value = 1;
	document.getElementById('form-account_level').value = 2;
	document.getElementById('form-employee_num').readOnly = false;

	editModal = new bootstrap.Modal(document.getElementById('editModal'));
	editModal.show();
}

//編集ボタンで呼び出される
function showModalEdit() {
	document.getElementById('form-employee_num').readOnly = true;

	userModal.hide();

	editModal = new bootstrap.Modal(document.getElementById('editModal'));
	editModal.show();
}


//登録／更新ボタンで呼び出される
function editUser() {
	reqEdit.open('POST', '/user/edit', true);
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
function showDetail(employeeNum) {
	//	フォームをクリアする
	inputList = document.getElementsByTagName('input')
	for (let i = 0; i < inputList.length; i++) {
		inputList[i].value = null;
	}

	reqDetail.open('GET', '/user/detail?employeeNum=' + employeeNum);
	reqDetail.send(null);

}

reqDetail.onload = function() {
	let json = JSON.parse(reqDetail.responseText);

	//	html側のidとjson側のkeyの対応
	let idKeyAAry = {
		'employee_num': 'employeeNum',
		'modal-name': 'name',
		'modal-name_kana': 'nameKana',
		'modal-department': 'department',
		'modal-tel_num': 'telNum',
		'modal-mail_address': 'mailAddress',
		'modal-age': 'age',
		'modal-account_level': 'accountLevel',
		'modal-position': 'position',
		'modal-register_date': 'registerDate',
		'modal-update_date': 'updateDate',
		'modal-retire_date': 'retireDate',
	};

	//	詳細モーダルに値をいれる
	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).innerText = json[key];
	})
	let gender = '';
	if (json.gender === 0) gender = '男';
	if (json.gender === 1) gender = '女';
	if (json.gender === 2) gender = 'その他';
	document.getElementById('modal-gender').innerText = gender;

	//	削除ボタンにアクションを設定
	document.getElementById('button-delete').onclick = deleteDevice.bind(null, json.employeeNum);


	//	編集モーダルに値をいれる
	idKeyAAry = {
		'form-employee_num': 'employeeNum',
		'form-name': 'name',
		'form-nameKana': 'nameKana',
		'form-department': 'department',
		'form-tel_num': 'telNum',
		'form-mail_address': 'mailAddress',
		'form-age': 'age',
		'form-gender': 'gender',
		'form-position': 'position',
		'form-register_date': 'registerDate',
		'form-update_date': 'updateDate',
		'form-retire_date': 'retireDate',

	}

	Object.entries(idKeyAAry).forEach(([id, key]) => {
		document.getElementById(id).value = json[key];
	})
	
	let accountLevel;
	if(json.accountLevel === '管理者') accountLevel = 1;
	if(json.accountLevel === '利用者') accountLevel = 2;
	document.getElementById('form-account_level').value = accountLevel;
	 

	// モーダルの表示
	userModal = new bootstrap.Modal(document.getElementById('userModal'));
	userModal.show();
}


// 削除を押したときに呼び出される
function deleteDevice(employeeNum) {
	result = confirm(employeeNum + 'のデータを削除します');

	if (result) {
		reqDelete.open('GET', '/user/delete?employeeNum=' + employeeNum);
		reqDelete.send(null);
	}
}

reqDelete.onload = function() {
	location.reload();
}