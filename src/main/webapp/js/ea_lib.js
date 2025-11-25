function reorderReelNo(objMediaName, objReelNoName, objTotalName) {
	
	var count = 0;	
	var noOfMedia = 0;	
	while (true)
	{
		var name = objMediaName + "_" + count;	
		var objTemp = eval(name);

		if (objTemp == undefined)
			break;
			
		if (objTemp.value != "")
			noOfMedia++;
		count++;
	}
	
	var nextCount = 1;
	for (var i=0; i < count; i++)
	{
		var name = objMediaName + "_" + i;	
		var objMedia = eval(name);
		if (objMedia.value != "")
		{		
			var objTemp = eval(objReelNoName + "_" + i);
			objTemp.value=""+(nextCount);
			objTemp = eval(objTotalName + "_" + i);
			objTemp.value=""+noOfMedia;
			nextCount++;		
		}
	}
}


function formatSearchMediaNo(objMediaNo) {
	var strMediaNo = removeSpace(objMediaNo);
	strMediaNo = strMediaNo.toUpperCase();
	objMediaNo.value = strMediaNo;
			
	if (strMediaNo.indexOf("%") >= 0)
		return true;

	strMediaNo = fmtMediaNo(strMediaNo);
	if (strMediaNo == null)
		return false;
	else {
		objMediaNo.value = strMediaNo;
		return true;
	}

}

function formatFixSizeMediaNo(objMediaNo) {
	var strMediaNo = removeSpace(objMediaNo);
	strMediaNo = strMediaNo.toUpperCase();
	objMediaNo.value = strMediaNo;

	strMediaNo = fmtMediaNo(strMediaNo);
	if (strMediaNo == null)
		return false;
	else {
		objMediaNo.value = strMediaNo;
		return true;
	}
}

function removeSpace(objMediaNo){
	var strMediaNo = objMediaNo.value;
	var count = 0;
	while (strMediaNo.indexOf(" ") >= 0){
		var pos = strMediaNo.indexOf(" ");
		strMediaNo = strMediaNo.substr(0, pos) + strMediaNo.substr(pos+1, strMediaNo.length);		
	}
	return strMediaNo;
}


function formatMediaNo(objMediaNo) {
	var strMediaNo = removeSpace(objMediaNo);
	strMediaNo = fmtMediaNo(strMediaNo);	
	if (strMediaNo == null)
		return false;
	else {
		objMediaNo.value = strMediaNo.toUpperCase();
		return true;
	}
}

function fmtMediaNo(strMediaNo) {
	var pattern = /[0-9]/;

	if (strMediaNo == null || strMediaNo == '')
		return null;
	if (strMediaNo.length < 8){
		//do reformatting
		//find the suffix position
		var count = -1;
		for (var i=strMediaNo.length-1; i >= 0; i--){
			var str = strMediaNo.substring(i, i+1);
			if (!pattern.test(str)){
				count = i+1;
				break
			}							
		}
		if (count < 0){
			// no prefix
			return leftpad(strMediaNo, "0", 8);
		}else{
			return strMediaNo.substring(0, count) + leftpad(strMediaNo.substring(count, strMediaNo.length), "0", 8-count);
		}
	}else
		return strMediaNo;
	
}


function formatMediaNoArrayOnInput(objMediaNoArray, event){
	var code = event.keyCode;
    if (code==13) //process only if press enter
	{    
		if (objMediaNoArray.value == "")
			return true;

		var isNewLineEnd = false;
	 	var strMediaNo = objMediaNoArray.value + "\n";
	
		strMediaNo = strMediaNo.toUpperCase();
//		strMediaNo = strMediaNo;
		var strMediaNoArray = strMediaNo.split("\n");		

		var strReturn = "";
		var delimitor = "";
		var addToEnd = "\n";
		for (var i=0; i < strMediaNoArray.length-1; i++){
			var str = strMediaNoArray[i].substring(0, strMediaNoArray[i].length - 1);
			if (str != null){
				while (str.length > 8){
					strReturn += delimitor + str.substring(0,8);
					str = str.substring(8,str.length);
					delimitor = "\n";
				}
				var retStr = fmtMediaNo(str.substring(0,str.length));
				if (retStr != null){
					strReturn += delimitor + retStr;
					delimitor = "\n";
				}
			}
		}

		objMediaNoArray.value = strReturn + "\n";
	}
	return false;
}

function formatMediaNoArray(objMediaNoArray) {		
	objMediaNoArray.value = objMediaNoArray.value.toUpperCase() + "\n";
//	objMediaNoArray.value = objMediaNoArray.value + "\n";
	var strMediaNoArray = objMediaNoArray.value.split("\n");
	
	var strReturn = "";
	var delimitor = "";	
	for (var i=0; i < strMediaNoArray.length; i++)
	{
		var strTemp = fmtMediaNo(strMediaNoArray[i].substring(0, strMediaNoArray[i].length - 1));
		if (strTemp != null)
		{
			strReturn = strReturn + delimitor + strTemp;
			delimitor = "\n";
		}
	}
	objMediaNoArray.value = strReturn;
	return true;
}

function leftpad(str, repeatPattern, toLength) {
	var newStr = str;
	for (var i=str.length; i < toLength; i++)
	{
		newStr = repeatPattern + newStr;
	}
	return newStr;
}

function formatHouseNo(objHouseNo) {
	objHouseNo.value = objHouseNo.value.toUpperCase();
	return true;
/*	
	var strHouseNo = fmtHouseNo(objHouseNo.value);
	if (strHouseNo == null)
		return false;
	else {
		objHouseNo.value = strHouseNo;
		return true;
	}
*/
}

function fmtHouseNo(strHouseNo) {
	var pattern = /[0-9]/;
	var size = 8;
	if (strHouseNo == null || strHouseNo == '')
		return null;
	if (strHouseNo.length < size){
		//do reformatting
		//find the suffix position
		var count = -1;
		for (var i=strHouseNo.length-1; i >= 0; i--){
			var str = strHouseNo.substring(i, i+1);
			if (!pattern.test(str)){
				count = i+1;
				break
			}							
		}
		if (count < 0){
			// no prefix
			return leftpad(strHouseNo, "0", size);
		}else{
			return strHouseNo.substring(0, count) + leftpad(strHouseNo.substring(count, strHouseNo.length), "0", size-count);
		}
	}else
		return strHouseNo;
	
}

function confirmDelete(objName) {
	var answer = window.confirm ('Are you sure to delete?');
	if (answer) {
		objName.value = "Y";
	}
	else {
		objName.value = "N";
	}
	return true;
}

function popup(strURL, strName, width, height){
	win = window.open(strURL , strName, 'width=' + width + ',height=' + height + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
    win.focus();
}

function uncheck(objField, objCheckBox){
	if (!objField.value == "")
		objCheckBox.checked = false;
}

function returnToWindowOpener(strCheckboxName, objReturnTo, objForm, strReturnFieldName, intCount){
	var returnValue = "";
	for (var k=0; k < intCount; k++){
		var objCheckbox = eval(strCheckboxName + "_" + k);
		
		if (objCheckbox != undefined){	
			if (objCheckbox.checked){	
				var objReturnField = eval(strReturnFieldName + "_" + k);
			    returnValue += objReturnField.value + "|";
			}
		}
	}

	if (returnValue != "" && returnValue != null){
		objReturnTo.value=returnValue;
		objForm.submit();
	}	
	window.close();
}

function returnToWindowOpener2(strCheckBoxName, objReturnTo, objForm, strReturnFieldName1, strReturnFieldName2, strReturnFieldName3, intCount){

	var returnValue = "";
		
	for (var k=0; k < intCount; k++){
		var objCheckbox = eval(strCheckBoxName + "_" + k);
		if (objCheckbox != undefined){	
			if (objCheckbox.checked){
				var objReturnField1 = eval(strReturnFieldName1 + "_" + k);
				var objReturnField2 = eval(strReturnFieldName2 + "_" + k);
				var objReturnField3 = eval(strReturnFieldName3 + "_" + k);				
			    returnValue += objReturnField1.value + ":" + objReturnField2.value + ":" + objReturnField3.value + "|";
			}				
		}
	}
		
	if (returnValue != "" && returnValue != null){
		objReturnTo.value=returnValue;
		objForm.submit();
	}	
	window.close();
}

// this function cannot return value in the page other than the first one
// use returnToWindowOpener instead
function returnToOpener(strCheckboxName, objReturnTo, objForm, strReturnFieldName){
	var count = 0;	
	var returnValue = "";
	var objCheckbox = eval(strCheckboxName + "_" + count);
	
	while(objCheckbox != undefined){	
		if (objCheckbox.checked){	
			var objReturnField = eval(strReturnFieldName + "_" + count);
		    returnValue += objReturnField.value + "|";
		}
		
		count++;
		objCheckbox = eval(strCheckboxName + "_" + count);
	}
	if (returnValue != "" && returnValue != null){
		objReturnTo.value=returnValue;
		objForm.submit();
	}	
	window.close();
}

// this function cannot return value in the page other than the first one
// use returnToWindowOpener2 instead
function returnToOpener2(strCheckBoxName, objReturnTo, objForm, strReturnFieldName1, strReturnFieldName2){
	var count = 0;	
	var returnValue = "";
	var objCheckbox = eval(strCheckBoxName + "_" + count);
	while(objCheckbox != undefined){	
		if (objCheckbox.checked){
			var objReturnField1 = eval(strReturnFieldName1 + "_" + count);
			var objReturnField2 = eval(strReturnFieldName2 + "_" + count);
		    returnValue += objReturnField1.value + ":" + objReturnField2.value + "|";
		}		
		count++;
		objCheckbox = eval(strCheckBoxName + "_" + count);
	}
		
	if (returnValue != "" && returnValue != null){
		objReturnTo.value=returnValue;
		objForm.submit();
	}	
	window.close();
}


/*
function callToOpener2(objRadio, objReturnTo, objForm, strReturnFieldName1, strReturnFieldName2){
	if (objRadio.length == undefined){
		// 1 radio button only
		if (objRadio.checked){	
			var objReturnField1 = eval(strReturnFieldName1 + "_" + objRadio.value);
			var objReturnField2 = eval(strReturnFieldName2 + "_" + objRadio.value);
			objReturnTo.value=objReturnField1.value + "|" + objReturnField2.value;
			objForm.submit();
		}
	}else{	
		//array of radio button
		for (var i=0; i < objRadio.length; i++){
			if (objRadio[i].checked){	
				var objReturnField1 = eval(strReturnFieldName1 + "_" + objRadio[i].value);
				var objReturnField2 = eval(strReturnFieldName2 + "_" + objRadio[i].value);
			    objReturnTo.value=objReturnField1.value + "|" + objReturnField2.value;
			    objForm.submit();
				break;
			}
		}
	}
	setTimeout('self.focus()',800);
}

function callToOpener(objRadio, objReturnTo, objForm, strReturnFieldName){
	if (objRadio.length == undefined){
		// 1 radio button only
		if (objRadio.checked){	
			var objReturnField = eval(strReturnFieldName + "_" + objRadio.value);
			objReturnTo.value=objReturnField.value;
			objForm.submit();
		}
	}else{	
		//array of radio button
		for (var i=0; i < objRadio.length; i++){
			if (objRadio[i].checked){	
				var objReturnField = eval(strReturnFieldName + "_" + objRadio[i].value);
			    objReturnTo.value=objReturnField.value;
			    objForm.submit();
				break;
			}
		}
	}
	setTimeout('self.focus()',800);
}
*/

function returnValueToOpener(objRadio, objReturnTo, objForm, strReturnFieldName){
	if (objRadio.length == undefined){
		// 1 radio button only
		if (objRadio.checked){	
			var objReturnField = eval(strReturnFieldName + "_" + objRadio.value);
			objReturnTo.value=objReturnField.value;
			objForm.submit();
		}
	}else{	
		//array of radio button
		for (var i=0; i < objRadio.length; i++){
			if (objRadio[i].checked){	
				var objReturnField = eval(strReturnFieldName + "_" + objRadio[i].value);
			    objReturnTo.value=objReturnField.value;
			    objForm.submit();
				break;
			}
		}
	}
	window.close();
}

