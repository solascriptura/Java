function validateDelete() {
	var checkboxList = new Array();
	checkboxList = document.getElementsByName('selectedNewsIds');
	var checked = false;
	for(var index = 0; index < checkboxList.length; index++) {
		if(checkboxList[index].checked == true) {
			checked = true;
			break;
		} 
	}
	if(checked == true) {
		if(confirm(confirmNewsDeleteMessage)) {
			return true;
		} else {
			return false;
		}
	}
	else {
		alert(noneNewChecked);
		return false;
	}
}

function confirmDelete() {
	if(confirm(confirmSingleNewDeleteMessage)) {
		return true;
	} else {
		return false;
	}
}

function validateSave() {
	var titleField;
	titleField = document.getElementById('titleField');
	var dateField;
	dateField = document.getElementById('dateField');
	var briefField;
	briefField = document.getElementById('briefField');
	var contentField;
	contentField = document.getElementById('contentField');
	if (titleField.value == "" 
		|| dateField.value == "" 
		|| briefField.value == "" 
		|| contentField.value == "") {
		alert(fieldsMustBeFilledMessage);
		return false;
	} else {
		var pattern = dateRegexp;
		if (!dateField.value.match(pattern)) {
			alert(wrongDateMessage);
			return false;
		} else {
			return true;
		
		}
	}
}