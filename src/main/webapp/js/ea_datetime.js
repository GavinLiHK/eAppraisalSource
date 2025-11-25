<!-- Original:  Mike Welagen (welagenm@hotmail.com) -->

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->

<!-- Begin
var deadlockCount = 0;

function checkdate(objName) {
	var datefield = objName;
	if (chkdate(objName) == false) {
		datefield.select();
		alert("Invalid date format");
		datefield.focus();
		return false;
	}else {
		return true;
   	}
}

function chkdate(objName) {
	//var strDatestyle = "US"; //United States date style
	var strDatestyle = "EU";  //European date style
	var strDate;
	var strDateArray;
	var strDay;
	var strMonth;
	var strYear;
	var intday;
	var intMonth;
	var intYear;
	var booFound = false;
	var datefield = objName;
	var strSeparatorArray = new Array("/");
	var intElementNr;
	var err = 0;
	var strMonthArray = new Array(12);
	strMonthArray[0] = "Jan";
	strMonthArray[1] = "Feb";
	strMonthArray[2] = "Mar";
	strMonthArray[3] = "Apr";
	strMonthArray[4] = "May";
	strMonthArray[5] = "Jun";
	strMonthArray[6] = "Jul";
	strMonthArray[7] = "Aug";
	strMonthArray[8] = "Sep";
	strMonthArray[9] = "Oct";
	strMonthArray[10] = "Nov";
	strMonthArray[11] = "Dec";
	strDate = datefield.value;
	
	if (strDate.length < 1) {
		return true;
	}	
	if (strDate.length < 6) {
		return false;
	}	
	
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
			strDateArray = strDate.split(strSeparatorArray[intElementNr]);
			if (strDateArray.length != 3) {
				err = 1;
				return false;
			} else {
				strDay = strDateArray[0];
				if (strDay.length > 2 || strDay.length < 1)
					return false;

				strMonth = strDateArray[1];
				if (strMonth.length > 2 || strMonth.length < 1)
					return false;

				strYear = strDateArray[2];
				if (strYear.length == 3 || strYear.length > 4 || strYear.length < 1)
					return false;
			}
			booFound = true;
	    }
	}
	
	if (booFound == false) {
		if (strDate.length != 6 && strDate.length != 8) {	
			return false;
		} else {
			strDay = strDate.substr(0, 2);
			strMonth = strDate.substr(2, 2);
			strYear = strDate.substr(4);
	   	}
	}

	
	if (isNaN(Number(strYear))) {
		return false;
	}
		
	if (strYear.length == 1)
		strYear = '200' + strYear;

	if (strYear.length == 2) {
		if (parseInt(strYear) > 80) {
			strYear = '19' + strYear;
		}else{
			strYear = '20' + strYear;
		}
	}

	// US style
	if (strDatestyle == "US") {
		strTemp = strDay;
		strDay = strMonth;
		strMonth = strTemp;
	}
	
	intday = parseInt(strDay, 10);
	if (isNaN(intday)) {
		err = 2;
		return false;
	}
	
	intMonth = parseInt(strMonth, 10);
	if (isNaN(intMonth)) {
		for (i = 0;i<12;i++) {
			if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
				intMonth = i+1;
				strMonth = strMonthArray[i];
				i = 12;
		   	}
		}		
		if (isNaN(intMonth)) {
			err = 3;
			return false;
	   	}
	}
	intYear = parseInt(strYear, 10);
	if (isNaN(intYear)) {
		err = 4;
		return false;
	}
	if (intMonth>12 || intMonth<1) {
		err = 5;
		return false;
	}
	if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
		err = 6;
		return false;
	}
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
		err = 7;
		return false;
	}
	if (intMonth == 2) {
		if (intday < 1) {
			err = 8;
			return false;
		}
		if (LeapYear(intYear) == true) {
			if (intday > 29) {
			err = 9;
			return false;
			}
		} else {
			if (intday > 28) {
				err = 10;
				return false;
			}
		}
	}
	if (strDatestyle == "US") {
		datefield.value = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
	} else {
		var strday = "" + intday;
		if (intday < 10)
			strday = "0" + intday;

		var strMonth = "" + intMonth;
		if (intMonth < 10)
			strMonth = "0" + intMonth;
			
		datefield.value = strday + "/" + strMonth + "/" + strYear;
	}
	return true;
}

function LeapYear(intYear) {
	if (intYear % 100 == 0) {
		if (intYear % 400 == 0) { return true; }
	} else {
		if ((intYear % 4) == 0) { return true; }
	}
	return false;
}

function doDateCheck(from, to) {
	if (Date.parse(from.value) <= Date.parse(to.value)) {
		alert("The dates are valid.");
	} else {
		if (from.value == "" || to.value == "") 
			alert("Both dates must be entered.");
		else 
			alert("To date must occur after the from date.");
   }
}

function checktime(objName) {
	var timefield = objName;
	if (chktime(objName) == false) {
		timefield.select();
//		alert("That time is invalid.  Please try again.");
		timefield.focus();
		return false;
	}else {
		return true;
   	}
}

function onlyDigit(inStr)	{
	var allDigit = "0123456789";
	var outStr = "";
	for(var i=0; i < inStr.length; i++)	{
		if((allDigit.indexOf(inStr.charAt(i)))!=-1)
			outStr += inStr.charAt(i);
	}
	return outStr;
}

function chktime(timefield) {
	deadlockCount++;
	if(deadlockCount > 50)
		return true;
	var strTime;
	var strHour;
	var strMin;
	var strSec;
	var strFrame;
	var intHour;
	var intMin;
	var intSec;
	var intFrame;

	var booFound = false;
	var strTimeSeparator = ":";
	var strFrameSeparator = ".";
	var intElementNr;
	var err = 0;
	strTime = timefield.value;
	var strTimeArray;
	
	if (strTime.length < 1) 
		return true;
	strTime = onlyDigit(strTime);
	if(strTime.length % 2 == 1)
		strTime = "0" + strTime;
	while (strTime.length < 8)
		strTime = strTime + "0";

	strTimeArray = strTime.split(strTimeSeparator);
	if (strTimeArray.length == 3) {
		strHour = strTimeArray[0];
		if (strHour.length > 2 || strHour.length < 1)
			return false;
			
		strMin = strTimeArray[1];
		if (strMin.length > 2 || strMin.length < 1)
			return false;
			
		strSec = strTimeArray[2];			
		if (strSec.length < 1)
			return false;
				
		strFrame = "00";		
		var strFrameArray = strSec.split(strFrameSeparator);
		if (strFrameArray.length > 1)
		{
			strSec = strFrameArray[0];	
			strFrame = strFrameArray[1];
		}		
		
		if (strSec.length < 1 || strSec.length > 2)
			return false;

		if (strFrame.length < 1 || strFrame.length > 2)
			return false;					
		booFound = true;
	}

	if (booFound == false) {
		if (strTime.length > 6) {
			strHour = strTime.substr(0, 2);
			strMin = strTime.substr(2, 2);
			strSec = strTime.substr(4, 2);
			strFrame = strTime.substr(6);
	   	}else
	   	{
			strHour = strTime.substr(0, 2);
			strMin = strTime.substr(2, 2);
			strSec = strTime.substr(4);
			strFrame = "00"
	   	}	   	
	}

	intHour = parseInt(strHour, 10);
	if (isNaN(intHour)) {
		err = 2;
		return false;
	}
	
	intMin = parseInt(strMin, 10);
	if (isNaN(intMin)) {
		err = 3;
		return false;
   	}

	intSec = parseInt(strSec, 10);
	if (isNaN(intSec)) {
		err = 4;
		return false;
	}

	intFrame = parseInt(strFrame, 10);
	if (isNaN(intFrame)) {
		err = 5;
		return false;
	}
	
	if (intMin<0 || intMin > 99) {
		err = 7;
		return false;
	}

	if (intMin>59) {
		var hrs = Math.floor(intMin / 60);
		intHour = intHour + hrs;
		intMin = intMin - hrs * 60;
	}
	
	if (intHour>99 || intHour<0) {
		err = 6;
		return false;
	}	

	if (intSec>59 || intSec<0) {
		err = 8;
		return false;
	}
	
	if (intFrame>24 || intFrame<0) {
		err = 9;
		return false;
	}	
	
	var hour = "" + intHour;
	if (hour < 10)
		hour = "0" + intHour;

	var min = "" + intMin;
	if (min < 10)
		min = "0" + intMin;

	var sec = "" + intSec;
	if (sec < 10)
		sec = "0" + intSec;

	var frame = "" + intFrame;
	if (frame < 10)
		frame = "0" + intFrame;

	timefield.value = hour + ":" + min + ":" + sec + "." + frame;
	
	return true;
}


function calculateDiff(strInTimeName, strOutTimeName, strDurationName)
{
	//get In Time
	var objInTime = eval("document.pageForm." + strInTimeName);
	var strInTime = objInTime.value;
	
	//get Out Time
	var objOutTime = eval("document.pageForm." + strOutTimeName);
	var strOutTime = objOutTime.value;

	//get Duration
	var objDuration = eval("document.pageForm." + strDurationName);
	
	if (strInTime.length <= 0 || strOutTime.length <= 0)	
	{
		//reset the duration
		objDuration.value = "";
		return true;
	}

	//Calculation Start
	var inFrame = parseFloat(strInTime.substring(9, 11)) * 40; // 25 frames per second
	var outFrame = parseFloat(strOutTime.substring(9, 11)) * 40; // 25 frames per second
	var inTime = new Date(0, 0, 0, strInTime.substring(0, 2), strInTime.substring(3, 5), strInTime.substring(6, 8), inFrame);	
	var outTime = new Date(0, 0, 0, strOutTime.substring(0, 2), strOutTime.substring(3, 5), strOutTime.substring(6, 8), outFrame);		

	//get the difference
	var diff = outTime - inTime;
	var isPositive = true;	
	if (diff < 0)
	{
		isPositive = false;
		diff = -diff;	
	}
	
	//convert miilsecond to hr:mi:ss		
	var diffHr = Math.floor(diff/1000/60/60);
	var diffMi = Math.floor((diff - diffHr*1000*60*60)/1000/60);
	var diffSec = Math.floor((diff - diffHr*1000*60*60 - diffMi*1000*60)/1000);
	var diffFrame = Math.floor((diff - diffHr*1000*60*60 - diffMi*1000*60 - diffSec*1000) / 40);

	var strDur = "";
	if (diffHr < 10)
		strDur = "0";		
	strDur += diffHr + ":";
	
	if (diffMi < 10)
		strDur += "0";
	strDur += diffMi + ":";
	
	if (diffSec < 10)
		strDur += "0";
	strDur += diffSec + ".";
	
	if (diffFrame < 10)
		strDur += "0";
	strDur += diffFrame;	
	
	if (!isPositive)
		strDur	= "-" + strDur
	objDuration.value = strDur;
	return true;	
}

function checkAndCalculateDifferenceTime(objName, strInTimeName, strOutTimeName, strDurationName) {
//	var timefield = checkTimeField(objName);
	var ret = chktime(objName);
	if (ret == false) {
		objName.focus();
		return false;
	} 
  else {
   	var objInTime = eval("document.pageForm." + strInTimeName);
  	var objOutTime = eval("document.pageForm." + strOutTimeName);
    var objDuration = eval("document.pageForm." + strDurationName) ;
    if(objInTime.value!='' && objOutTime.value!='')
  		calculateDiff(strInTimeName, strOutTimeName, strDurationName)
    return true;
  }
}

function checkTimeField(objName) {
	var timefield = objName;
	if (chktime(objName) == false) {
		timefield.select();
		timefield.focus();
		return false;
	}else {
		return true;
   	}
}

function checkTime(obj) {
  if (checkTimeField(obj)) 
    obj.value = obj.value.substring(0, 8) ;
}

function checkPositiveDuration(objName, strInTimeName, strOutTimeName, strDurationName) {
  if (checkAndCalculateDifferenceTime(objName, strInTimeName, strOutTimeName, strDurationName)) {
   	var objOutTime = eval("document.pageForm." + strOutTimeName);
  	var objDuration = eval("document.pageForm." + strDurationName);
      if (objDuration.value.length>11) {
        objOutTime.select() ;
        objOutTime.focus() ;
        return false ;
      }
  } 
}

function calculateOneDiff(strInTimeName, strOutTimeName, strDurationName)
{
	//get In Time
	var objInTime = eval("document.pageForm." + strInTimeName);
	var strInTime = objInTime.value;
	
	//get Out Time
	var objOutTime = eval("document.pageForm." + strOutTimeName);
	var strOutTime = objOutTime.value;

	//get Duration
	var objDuration = eval("document.pageForm." + strDurationName);
	var strDuration = objDuration.value;
	
	/*
	if (strInTime.length <= 0 || strOutTime.length <= 0)	
	{
		//reset the duration
		objDuration.value = "";
		return true;
	} */

	var strA = '';
	var strB = '';
	
	if (strInTime == '') {
		strA = strOutTime;
		strB = strDuration;
		
	} else if (strOutTime == '') {
		strA = strDuration;
		strB = strInTime;
	} else if (strDuration == '') {
		strA = strOutTime;
		strB = strInTime;
	}

	//Calculation Start
	var inFrame = parseFloat(strB.substring(9, 11)) * 40; // 25 frames per second
	var outFrame = parseFloat(strA.substring(9, 11)) * 40; // 25 frames per second
	var inSec = parseFloat(strB.substring(6, 8));
	var outSec = parseFloat(strA.substring(6, 8));
	var inMin = parseFloat(strB.substring(3, 5));
	var outMin = parseFloat(strA.substring(3, 5));
	var inHr = parseFloat(strB.substring(0, 2));
	var outHr = parseFloat(strA.substring(0, 2));
	
	var inTime = new Date(0, 0, 0, strB.substring(0, 2), strB.substring(3, 5), strB.substring(6, 8), inFrame);	
	var outTime = new Date(0, 0, 0, strA.substring(0, 2), strA.substring(3, 5), strA.substring(6, 8), outFrame);		

	//get the difference
	var diff = '';
	if (strOutTime == '') diff = inFrame + outFrame + (inSec + outSec) * 1000 + (inMin +outMin) * 60 * 1000
								+ (inHr + outHr) * 60 * 60 * 1000;
	else diff = outTime - inTime;

	var isPositive = true;	
	if (diff < 0)
	{
		isPositive = false;
		diff = -diff;	
	}
	
	//convert miilsecond to hr:mi:ss		
	var diffHr = Math.floor(diff/1000/60/60);
	var diffMi = Math.floor((diff - diffHr*1000*60*60)/1000/60);
	var diffSec = Math.floor((diff - diffHr*1000*60*60 - diffMi*1000*60)/1000);
	var diffFrame = Math.floor((diff - diffHr*1000*60*60 - diffMi*1000*60 - diffSec*1000) / 40);

	var strDur = "";
	if (diffHr < 10)
		strDur = "0";		
	strDur += diffHr + ":";
	
	if (diffMi < 10)
		strDur += "0";
	strDur += diffMi + ":";
	
	if (diffSec < 10)
		strDur += "0";
	strDur += diffSec + ".";
	
	if (diffFrame < 10)
		strDur += "0";
	strDur += diffFrame;	
	
	if (!isPositive)
		strDur	= "-" + strDur

	if (strInTime == '') {
		objInTime.value = strDur;
	} else if (strOutTime == '') {
		objOutTime.value = strDur;
	} else if (strDuration == '') {
		objDuration.value = strDur;
	}
	
	//objDuration.value = strDur;
	return true;	
}


function checkAndCalculateTimeDiff(objName, strInTimeName, strOutTimeName, strDurationName) {
	var ret = chktime(objName);
	if (ret == false) {
		objName.focus();
		return false;
	} 
  else {
   	var objInTime = eval("document.pageForm." + strInTimeName);
  	var objOutTime = eval("document.pageForm." + strOutTimeName);
    var objDuration = eval("document.pageForm." + strDurationName);
    if( (objInTime.value !='' && objOutTime.value !='' && objDuration.value == '') || 
    	(objInTime.value =='' && objOutTime.value !='' && objDuration.value != '') || 
    	(objInTime.value !='' && objOutTime.value =='' && objDuration.value != '')
    	
    ) {
  		calculateOneDiff(strInTimeName, strOutTimeName, strDurationName);
  	}
  		
  	
    return true;
  }
}


//========================
// Added by Jack
//========================
function chk0630time(timefield){
//alert("calling chk0630time");
var strTime;
var strHour;
var strMin;
var intHour;
var intMin;
var strTimeArray;

strTime = timefield.value;
if (strTime.length < 1) 
		return true;
	strTime = onlyDigit(strTime);
	//if(strTime.length % 2 == 1)
	//	strTime = "0" + strTime;
while (strTime.length < 4)
		strTime = strTime + "0";


			strHour = strTime.substr(0, 2);
			strMin = strTime.substr(2, 2);
	
	intHour = parseInt(strHour, 10);
	if (isNaN(intHour)) 
		return false;
	
intMin = parseInt(strMin, 10);
	if (isNaN(intMin)) 
		return false;
   	

if (intMin>59) {
		var hrs = Math.floor(intMin / 60);
		intHour = intHour + hrs;
		intMin = intMin - hrs * 60;
	}

if (intHour>29 || intHour<0) {
		alert("Invalid time format!");
		return false;
	}	
else if (intHour>=0 && intHour<=5){
alert("Invalid time format!");
return false;
}
var hour = "" + intHour;
	if (hour < 10)
		hour = "0" + intHour;

var min = "" + intMin;
	if (min < 10)
		min = "0" + intMin;

timefield.value = hour + ":" + min ;
	
	return true;	
	
	
	}
//========================
// Added by Jack
//========================
	function check0630TimeField(objName) {
	//alert("check0630TimeField");
	var timefield = objName;
	if (chk0630time(objName) == false) {
		timefield.select();
		timefield.focus();
		return false;
	}else {
		return true;
   	}
}

// Added by Keith
//========================
function atsChkAndCalEndTime(objName, strStartTimeName, strEndTimeName) {
	var ret = chkPosInt(objName);
	if (ret != false) {
	   	var objStartTime = eval("document.pageForm." + strStartTimeName);
  		var objEndTime = eval("document.pageForm." + strEndTimeName);
    	if(objStartTime.value != '' && objName.value != ''){
  			atsCalEndTime(objName, objStartTime, objEndTime);
  		}
 		return true;
	}
	else {
 	 	return false;
	}
}

function atsChkStartAndCalTime(objName, strEndTimeName, strDurationName) {
	var ret = check0630TimeField(objName);
	if (ret == false) {
		return false;
	}
	else {
  		var objEndTime = eval("document.pageForm." + strEndTimeName);
	 	var objDuration = eval("document.pageForm." + strDurationName);
    	if(objDuration.value != '' && objName.value != ''){
  			atsCalEndTime(objDuration, objName, objEndTime);
  		}
		return true;
  	}
}

function atsChkStAndGenBkName(objName, strBreakName) {
	var ret = check0630TimeField(objName);
	var objBreakName = eval("document.pageForm." + strBreakName);
	if (objBreakName.value == '' && ret == true) {
		objBreakName.value = objName.value.substring(0, 2) + objName.value.substring(3, 5);
	}
	return ret;
}

function chkPosInt(objName) {
	var intObj;
	intObj = objName.value.valueOf();
	if(!isNaN(intObj)){
		if(intObj > 0) {
			return true;
		} else {
			objName.select();
			objName.focus();
			alert("Please enter a positive number");
			return false;
		}
	} else {
		objName.select();
		objName.focus();
		alert("Please enter a positive number");
		return false;
	}
}

function atsCalEndTime(objName, objStartTime, objEndTime) {
	//get Start Time
	//var objStartTime = eval("document.pageForm." + strStartTimeName);
	var strStartTime = objStartTime.value;
	
	//get End Time
	//var objEndTime = eval("document.pageForm." + strEndTimeName);
	var strEndTime = objEndTime.value;

	var inSec = 0;
	//var outSec = 0;
	var inMin = parseFloat(strStartTime.substring(3, 5));
	//var outMin = parseFloat(strA.substring(3, 5));
	var inHr = parseFloat(strStartTime.substring(0, 2));
	//var outHr = parseFloat(strA.substring(0, 2));
	
	var diff = parseInt(objName.value);
	var secStartTime = inHr * 60 * 60 + inMin * 60;
	var secEndTime = secStartTime + diff;
		
	var diffHr = Math.floor(secEndTime/60/60);
	var diffMi = Math.floor((secEndTime - diffHr*60*60)/60);
	var diffSec = secEndTime - diffHr*60*60 - diffMi*60;
	
	var strEnd= "";
	if (diffHr < 10)
		strEnd = "0";		
	strEnd += diffHr + ":";
	
	if (diffMi < 10)
		strEnd += "0";
	strEnd += diffMi;
	
	objEndTime.value = strEnd;
	return true;
}

function atsCpySpotToDuration(objName, strStartTimeName, strEndTimeName, strDurationName) {
	var ret = chkPosInt(objName);
	var objDuration = eval("document.pageForm." + strDurationName);
	if (ret != false && (objDuration.value <= objName.value || objDuration.value == '')) {
		objDuration.value = objName.value;
		ret = atsChkAndCalEndTime(objDuration, strStartTimeName, strEndTimeName);
	}
	return ret;
}


//========================
// Added by Jeff
//========================
function checkMonthYear(objName) {
	var pass = true;
	var strObj = objName.value;
	if (strObj != "") {
		var exp = new RegExp("^([0-1]?[0-9]+)\/([0-9]?[0-9]?[0-9][0-9])$");
		var matched = exp.exec(strObj);
		if (matched == null) {
			pass = false;
		} else {
			pass &= (!isNaN(matched[1].valueOf()) && matched[1].valueOf() != '')
				? (matched[1].valueOf() > 0 && matched[1].valueOf() <= 12)
				: false;
			pass &= (!isNaN(matched[2].valueOf()) && matched[2].valueOf() != '')
				? (matched[2].valueOf() > 0)
				: false;
		}
		if (!pass) {
	        objName.select();
	        objName.focus();
	        alert("Please enter month format in mm/yyyy");
		}
	}
	return pass;
}

//-----------------------------------------------//
//-  Added by Wander							-//
//-  Used in AssignOfficer, WorkflowDetail		-//
//-----------------------------------------------//
function doDeadlineDateCheck(previous, checking, previousDateTitle, checkingDateTitle, currentIndex, 							 checkingIndex) {
	if (previous=="" || checking=="" || previous==null || checking==null)
		return "";
	var checkingDateArray;
	var checkingDay;
	var checkingMonth;
	var checkingYear;
	var previousDateArray
	var previousDay;
	var previousMonth;
	var previousYear;
	var strSeparatorArray = new Array("/");
	var intElementNr;
	var err = 0;
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (checking.indexOf(strSeparatorArray[intElementNr]) != -1) {
			checkingDateArray = checking.split(strSeparatorArray[intElementNr]);
			if (checkingDateArray.length != 3) {
				err = 1;
				return false;
			} else {
				checkingDay = checkingDateArray[0];
				checkingMonth = checkingDateArray[1];
				checkingYear = checkingDateArray[2];
			}
		}
		if (previous.indexOf(strSeparatorArray[intElementNr]) != -1) {
			previousDateArray = previous.split(strSeparatorArray[intElementNr]);
			if (previousDateArray.length != 3) {
				err = 1;
				return false;
			} else {
				previousDay   = previousDateArray[0];
				previousMonth = previousDateArray[1];
				previousYear  = previousDateArray[2];
			}
		}
	}
	if (checkingIndex > currentIndex){
		if ((checkingYear > previousYear) ||
			(checkingYear == previousYear && checkingMonth > previousMonth) ||
			(checkingYear == previousYear && checkingMonth == previousMonth && checkingDay >= previousDay)){
			return "";
		}
		return checkingDateTitle + "\n";
// Modified by FW 
//		return checkingDateTitle + " should exceed " + previousDateTitle + "\n";
	}else{
		if ((checkingYear < previousYear) ||
			(checkingYear == previousYear && checkingMonth < previousMonth) ||
			(checkingYear == previousYear && checkingMonth == previousMonth && checkingDay <= previousDay)){
			return "";
		}
		return checkingDateTitle + " should not exceed " + previousDateTitle + "\n";
	}
	return "";
}

function checkIntervalFormat(interval){
	if (isNaN(interval.value)){
		interval.select();
		interval.focus();
		return false;
	}else
		return true;
}