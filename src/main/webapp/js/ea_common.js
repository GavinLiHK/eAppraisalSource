//##### force forward ########
//      history.forward();
//############################

var dateFormat = "dd/mm/yyyy";
var imgDir = "/ea/images/";
var childWin;
var childWinName;
if (window.name == "asapsChildWin1") {
  childWinName="asapsChildWin2";
} else if (window.name == "asapsChildWin2") {
  childWinName="asapsChildWin3";
} else {
  childWinName = "asapsChildWin1";
}
var idField;
var codeField;
var descField;

var field1;
var field2;
var field3;
var field4;
var field5;
var field6;

var newwin ; 
//document.body.onunload = closeChildWin;
//document.body.onactivate = focusChildWin;
//document.body.onfocus = focusChildWin;
//document.body.onclick = focusChildWin;
//document.body.ondblclick = focusChildWin;
//document.body.onkeypress = onBodyKeypress;
//document.body.onkeydown = onBodyKeypress;

function onBodyKeypress() {
    if (event.keyCode == 8) {
    	var comType = document.activeElement.getAttribute("type");
    	var catchThis = false;
    	catchThis |= (comType == null);
    	catchThis |= (comType != null && comType.indexOf("text") < 0);
    	if (comType != null && comType.indexOf("text") >= 0) {
    		var catchToo = false;
    		catchToo |= document.activeElement.disabled;
    		catchToo |= (document.activeElement.getAttribute("readonly") == true);
    		if (catchToo) catchThis = true;
    	}
		if (catchThis) {
        	event.returnValue = false;
        }
	}
}

function closeChildWin() {
	if (childWin != null && !childWin.closed) {
		childWin.close();
	}
}

function focusChildWin() {
	if (childWin != null && !childWin.closed) {
		childWin.focus();
	}
}

function pickDate(inSelectedDateField) {
   popUpCalendar(inSelectedDateField, inSelectedDateField, dateFormat);
}

function returnToOpener(id, code, desc) {
	window.close();

	if (opener != null) {
		opener.returnValue(id, code, desc);
		opener.focus();
	}
}

function returnValue(id, code, desc) {
  if (idField != null) {
    idField.value = id;
  }
  
  if (codeField != null) {
    codeField.value = code;
  }
  
  if (descField != null) {
    descField.value = desc;
  }

  if (idField != null) {
    if (idField.onchange) 
      idField.onchange();
  }
  
  if (codeField != null) {
    if (codeField.onchange)
      codeField.onchange();
  }
  
  if (descField != null) {
    if (descField.onchange) 
      descField.onchange();
  }
}

function returnNumValue(id, code, desc) {
    idField.value = id;
  
    codeField.value = code;
  
    descField.value = desc;

    if (idField.onchange) 
      idField.onchange();
  
    if (codeField.onchange)
      codeField.onchange();
  
    if (descField.onchange) 
      descField.onchange();
}


function returnToOpener3(f1, f2, f3, f4, f5, f6) {
	window.close();
	if (opener != null) {
		opener.returnValue3(f1, f2, f3, f4, f5, f6);
		opener.focus();
	}
}

function returnValue3(f1, f2, f3, f4, f5, f6) {
  
  if (field1 != null) {
    field1.value = f1;
  }
  
  if (field2 != null) {
    field2.value = f2;
  }
  
  if (field3 != null) {
    field3.value = f3;
  }
  if (field4 != null) {
    field4.value = f4;
  }
  if (field5 != null) {
    field5.value = f5;
  }
  if (field6 != null) {
    field6.value = f6;
  }
  
  if (field1 != null) {
    if (field1.onchange) 
      field1.onchange();
  }
  
    if (field2 != null) {
    if (field2.onchange) 
      field2.onchange();
  }

  if (field3 != null) {
    if (field3.onchange) 
      field3.onchange();
  }

  if (field4 != null) {
    if (field4.onchange) 
      field4.onchange();
  }

  if (field5 != null) {
    if (field5.onchange) 
      field5.onchange();
  }

  if (field6 != null) {
    if (field6.onchange) 
      field6.onchange();
  }

}





function enableAll() {
  for(i = 0; i < document.all.length; i++){
    if (document.all(i).disabled) {
      document.all(i).disabled=false;
    }
  }

  return true;
}
  



topZIndex = 10000;

function oLayer() {
   this._oBody = document.getElementsByTagName("BODY").item(0);
   this._oHelperIframe = document.createElement("IFRAME");
   this._oLayerDiv = document.createElement("DIV");
   this.visibility = "hidden";
   this._oHelperIframe.style.border = 0;
   this._oHelperIframe.width = 0;
   this._oHelperIframe.height = 0;
   this._oHelperIframe.style.position = "absolute";
   this._oHelperIframe.src = "/ea/Jsp/hello.jsp";
   this._oBody.appendChild(this._oHelperIframe);
   this._oLayerDiv.style.border = 0;
   this._oLayerDiv.width = 0;
   this._oLayerDiv.height = 0;
   this._oLayerDiv.style.position = "absolute";
   this._oBody.appendChild(this._oLayerDiv);
   // Should return the div actual width.
   this._getLayerDivWidth = function() {
      // We are checking the inner table because of a bug in NS/Mozilla with the DIV-->offsetWidth
      var tableWidth = "" + this._oLayerDiv.getElementsByTagName("table").item(0).offsetWidth;
      if(tableWidth.indexOf('px') > - 1) {
         return parseInt(tableWidth.substring(0, tableWidth.infexOf('px')));
      }
      else {
         return tableWidth;
      }
   }
   // Should return the div actual Height.
   this._getLayerDivHeight = function() {
      // We are checking the inner table because of a bug in NS/Mozilla with the DIV-->offsetHeight
      var tableHeight = "" + this._oLayerDiv.getElementsByTagName("table").item(0).offsetHeight;
      if(tableHeight.indexOf('px') > - 1) {
         return parseInt(tableHeight.substring(0, tableHeight.infexOf('px')));
      }
      else {
         return tableHeight;
      }
   }
   this._attachToEvent = function(obj, name, func) {
      name = name.toLowerCase();
      // Add the hookup for the event.
      if( typeof(obj.addEventListener) != "undefined") {
         if(name.length > 2 && name.indexOf("on") == 0) name = name.substring(2, name.length);
         obj.addEventListener(name, func, false);
      }
      else if( typeof(obj.attachEvent) != "undefined") {
         obj.attachEvent(name, func);
      }
      else {
         if(eval("obj." + name) != null) {
            // Save whatever defined in the event
            var oldOnEvents = eval("obj." + name);
            eval("obj." + name) = function(e) {
               try
               {
                  func(e);
                  eval(oldOnEvents);
               }
               catch(e) {
               }
            };
         }
         else {
            eval("obj." + name) = func;
         }
      }
   }
   // Will move the div and the helper iframe to the given X and Y position
   this.moveTo = function(xPos, yPos) {
      // Set the Y position
      if(parseInt(this._getLayerDivHeight()) > parseInt(this._oBody.clientHeight)) {
         // Open to top
         this._oLayerDiv.style.top = this._oBody.scrollTop;
      }
      else if(parseInt(yPos) - parseInt(this._oBody.scrollTop) + parseInt(this._getLayerDivHeight()) > parseInt(this._oBody.clientHeight)) {
         // Open to bottom
         this._oLayerDiv.style.top = this._oBody.clientHeight - this._getLayerDivHeight() + this._oBody.scrollTop;
      }
      else {
         // Open to yPos
         this._oLayerDiv.style.top = yPos;
      }
      // Set the X position
      if(parseInt(xPos) - parseInt(this._oBody.scrollLeft) + parseInt(this._getLayerDivWidth()) > parseInt(this._oBody.clientWidth)) {
         // Open to right
         this._oLayerDiv.style.left = this._oBody.clientWidth - this._getLayerDivWidth() + this._oBody.scrollLeft;
      }
      else {
         // Open to xPos
         this._oLayerDiv.style.left = xPos;
      }
      this.refresh();
   }
   this.show = function() {
      this._oHelperIframe.style.zIndex = topZIndex++;
      this._oLayerDiv.style.zIndex = topZIndex++;
      this.visibility = 'show';
      this._oHelperIframe.style.visibility = 'visible';
      this._oLayerDiv.style.visibility = 'visible';
   }
   this.hide = function() {
      this.visibility = 'hidden';
      this._oHelperIframe.style.visibility = 'hidden';
      this._oLayerDiv.style.visibility = 'hidden';
   }
   this.setContent = function(msg) {
      //    var divContent = "<table style='border:1px solid black;background-color:LightGoldenrodYellow' cellspacing='0' cellpading='0'><tr><td>" + msg + "</td></tr></table>";
      var divContent = msg;
      this._oLayerDiv.innerHTML = divContent;
      this.refresh();
   }
   this.refresh = function() {
      this._oHelperIframe.style.top = this._oLayerDiv.style.top;
      this._oHelperIframe.style.left = this._oLayerDiv.style.left;
      this._oHelperIframe.width = this._getLayerDivWidth();
      this._oHelperIframe.height = this._getLayerDivHeight();
   }
}


/*
	check or uncheck all checkboxes in a datatable 
*/
function checkAll(strCheckBoxName, turnOn){
	var count = 0;
	while (true){
		var name = strCheckBoxName + "_" + count;	
		var objCheckBox = eval(name);

		if (objCheckBox == undefined)
			break;
			
		objCheckBox.checked = turnOn;
		count++;
	}
}


function replaceAll( str, from, to ) {
    var idx = str.indexOf( from );

    while ( idx > -1 ) {
        str = str.replace( from, to ); 
        idx = str.indexOf( from );
    }
    return str;
}


function sumAllFieldsFloat(fieldName){
	var count = 0;
	var sum = 0;
	while (true){
		var name = fieldName + "_" + count;	
		var objField = eval(name);

		if (objField == undefined)
			break;
			
		sum += parseFloat(replaceAll(objField.value,',',''));
		count++;
	}
	return sum;
}






/*
	check or uncheck all checkboxes in a datatable 
*/
function pageCheckAll(strCheckBoxName, turnOn, startIndex, endIndex){
	for (var k=startIndex; k <= endIndex; k++){
		var name = strCheckBoxName + "_" + k;
		var objCheckBox = eval(name);
		if (objCheckBox == undefined){
		}else{
			objCheckBox.checked = turnOn;
		}
	}
}


/*
	popup a page & then redirect to other page
*/
function popupRedirect(strRedirectLink, strPopupLink, strWinName, height, width){
	win = window.open(strPopupLink, strWinName, 'width=' + width + ',height=' + height + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
	window.location = strRedirectLink;
	win.focus();
}

/*
	popup 2 pages & then redirect to other page
*/
function popupRedirect2Win(strRedirectLink, strPopupLink1, strWinName1, strPopupLink2, strWinName2, height, width){
	win = window.open(strPopupLink1, strWinName1, 'width=' + width + ',height=' + height + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
	win2 = window.open(strPopupLink2, strWinName2, 'width=' + width + ',height=' + height + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
 	window.location = strRedirectLink;
    win.focus();     	
    win2.focus();     	
}

/*
  popup confirmation when delete button is clicked
*/
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


function confirmSave(objName, msg) {
	var answer = window.confirm (msg);
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

function pickListSelectAll(obj){
	for (i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}	
}

function pickListDeSelectAll(obj){
	for (i=0; i<obj.options.length; i++) {
		obj.options[i].selected = false;
	}	
}

function checkNumber(obj) {
	if (!isNumber(obj)) {
		obj.select();
		obj.focus();
		return false;
	} 
	else 
		return true;
}

function isNumber(obj) {
  var temp = obj.value.replace(/[0-9]/g, '') ;
  if (temp.length == 0) 
    return true ;
  else
    return false ;
}

function openApplet(target, parms, name, h, w, s, t, m) {
  if (newwin != null) 
    newwin.close() ;
  newwin = window.open(target + parms, name, 'height=' + h + ',width=' + w + ',status=' + s + ',toolbar=' + t + ',menubar=' + m);
}

function openApplet2(target, parms, name, h, w, s, t, m,d) {
	opener.focus();
	opener.document.applets['htmlPageTopContainer_pageForm_box1_ProgrammeScheduler'].changeDate(d);
}

function validateResult(s) {
	//opener.focus();
	//opener.document.applets['htmlPageTopContainer_pageForm_box1_ProgrammeScheduler'].validateResult(s);
//	opener.document.htmlPageTopContainer_pageForm_box1_ProgrammeScheduler.validateResult(s);
}

function checkUncheck(prefix) {
	var count = 0;
	var check = false;
	var found = false;
	while (count <= 200){
		var obj;
		try{
			obj = eval("document.pageForm." + prefix + "_" + count);			
		}catch(e){}		
		
		if (obj == undefined){
		}else{
			if (!found){
				check = !obj.checked;
				found = true;
			}
			if (!obj.disabled){
			   obj.checked = check;
			}
		}
		count++;
	}
}

//-----------------------------------------
//--   roundOff(value, precision)
//-- Added by Jack
//-----------------------------------------
function roundOff(value, precision) {
value = "" + value;
precision = parseInt(precision);
var whole = "" + Math.round(value * Math.pow(10, precision));
var decPoint = whole.length - precision;
if(decPoint != 0) {
result = whole.substring(0, decPoint);
result += ".";
result += whole.substring(decPoint, whole.length);
}
else {
result = whole;
}
return result;
}


//-----------------------------------------
//-- Encode chinese character when pass to crystal report  
//-- Copy from style report sree/asaps?op=resource&name=%2Finetsoft%2Fsree%2Finternal%2Fmarkup%2FencodeUtil%2Ejs
//-- Modified by Cathy Chan
//-----------------------------------------
function byteEncode(fieldObj, isEncodeAll) {

	var str = fieldObj.value;
   if((str == null) || (str == "")) {
     return "";
   }

   if(isEncodeAll == null) {//default value is false.
      isEncodeAll = false;
   }

   var ret = '';

   for(var i=0; i < str.length; i++) {
      var ch = str.charAt(i);
      var code = str.charCodeAt(i);

      if(code < 128 && ch != '[' && !isEncodeAll && ch != '\''
         && ch != '=') {
         ret += ch;
      }
      else {
         ret += "[" + code.toString(16) + "]";
      }
   }	
	fieldObj.value = ret;
}

function formatCurrency(objNum) 
{
	var num = objNum.value;
	objNum.value = 	formatCurrencyStr(num);
}

function formatCurrencyStr(num) 
{
	num = num.toString().replace(/\$|\,/g,'');
	if (num == "")
		return "";
	
	if(isNaN(num)){
		return "";
	}
	
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
		cents = "0" + cents;
	
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3))+','+
	
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + num + '.' + cents);
}



function formatNumber(objNum, noOfDecimal){
	var num = objNum.value;
	objNum.value = formatNumberStr(num, noOfDecimal);
}

function formatNumberStr(num, noOfDecimal){

	var times = Math.pow(10, noOfDecimal);
	var noOfZero = Math.pow(10, noOfDecimal-1);

	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
		num = "0";
	
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*times+0.50000000001);
	cents = num%times;
	num = Math.floor(num/times).toString();

	cents = "" + cents;
	
	for (var k=cents.length; k < noOfDecimal; k++){
		cents = "0" + cents;
	}
	
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3))+','+
	
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + num + '.' + cents);
}

//------------------------
// remove comma in currency field
//------------------------

function filterNum(str) {
	re = /^\$|,/g;
	// remove "$" and ","
	return str.replace(re, "");
}


//-----------------------------
// Add by Jack on 9 th June 2005  
// If non negative number,return true.
//-----------------------------

function checkNotNegative(obj) {
	if (!isFloatNumber(obj)) {
		obj.select();
		obj.focus();
		return false;
	} 
	

if(obj.length > 0){
floatValue = parseFloat(obj.value);
if(floatValue > 0){
obj.select();
		obj.focus();
		return false;
                 }

}
else return true;




		return true;
}

//-----------------------------
// Add by Jack on 9 th June 2005  
//-----------------------------
function isFloatNumber(obj){

if(isNumber(obj))
return true;


else {
var temp = obj.value.replace(/[0-9]/g, '') ;

if(temp.indexOf('.') == -1 || temp.length > 1)
return false;

else if(obj.value.charAt(0) != '.' )
return true;

else return false;

}

}

function searchMessage(inIdField, inCodeField, inDescField) 
{
	if ( confirm("This action will override the existing content.  Are you sure to proceed?" ) == true )
	{
	    childWin = window.open("/ea/Jsp/common/SearchMessage.jsp?queryMode=Y&pageAction=reset", childWinName, "height=400px,width=600px,location=no,menubar=no,resizable=no,scrollbars=yes,status=yes,titlebar=no;toolbar=no");
	    idField=inIdField;
	    codeField=inCodeField;
	    descField=inDescField;
	}
}

function searchEmployee(inIdField, inCodeField, inDescField) {
    childWin = window.open("/ea/Jsp/common/EmployeeEnquiry.jsp?queryMode=Y&pageAction=reset", childWinName, "height=400px,width=600px,location=no,menubar=no,resizable=no,scrollbars=yes,status=yes,titlebar=no;toolbar=no");
    idField=inIdField;
    codeField=inCodeField;
    descField=inDescField;
  }

function searchEmployeeByName(inIdField, inCodeField, inDescField) {
	desc = encodeQueryStringData(inDescField.value);
    childWin = window.open("/ea/Jsp/common/EmployeeEnquiry.jsp?queryMode=Y&pageAction=reset&autoClose=Y&employeeFullName="+desc, childWinName, "height=400px,width=600px,location=no,menubar=no,resizable=no,scrollbars=yes,status=yes,titlebar=no;toolbar=no");
    idField=inIdField;
    codeField=inCodeField;
    descField=inDescField;
  }


function encodeQueryStringData(strUrl) {
	if (strUrl == null)
		return '';
	return strUrl.replace(/\%/g, '%25').replace(/ /g, '+').replace(/\&/g, '%26').replace(/=/g, '%3D');
}


/*
 * Function to execute a given script on dynamic element names, which based on sofia talble row id
 *
 * Parameters:
 *  1) obj                 Element to get row id (e.g. ABCDE_Fidld_0)
 *  2) maskForRowId        Mask for extract row id (e.g. ABCDE_Fidld)
 *  3) elementNames        Array to store the targeted element names (e.g. new Array('ABCDE_From_Fidld', 'ABCDE_Operator_Fidld')
 *  4) evalVal             Script to run (e.g. "{0}.value=''; {1}.value='';")
 *
 * Example:
 *   evalInTableRow(
 *     document.pageForm.ABCDE_Fidld_0,
 *     'ABCDE_Fidld',
 *     new Array('ABCDE_From_Fidld', 'ABCDE_Operator_Fidld'),
 *     '{0}.value=\'\'; {1}.value=\'\';'
 *   );
 *
 * Example result:
 *   eval('ABCDE_From_Fidld_0.value=\'\'; ABCDE_Operator_Fidld_0.value=\'\');
 *
 */
function evalInTableRow(obj, maskForRowId, elementNames, evalVal) {
	var suffixOfRow = obj.name;
	var rowId = suffixOfRow.replace(new RegExp(maskForRowId,'g'), '');
	
	var i, token, re, code2run = evalVal;
	for (i=0; i<elementNames.length; i++) {
		token = '\\\{' + i + '\\\}';
		re = new RegExp(token,'g');
		code2run = code2run.replace(re, elementNames[i] + rowId);
	}
	eval(code2run);
}


function countBytes(s) {
	var ascCnt = 0;
	for (var n = 0; n < s.length; n++) {
        var c = s.charCodeAt(n);
        if (c <= 0x7F) {
	    	//  0x00 - 0x7F:  Emit as single byte, unchanged
	    	ascCnt++;
	    } else if ((c >= 0x80) && (c <= 0x7FF)) {
	    	ascCnt++;
	    	ascCnt++;
	    } else {
	    	ascCnt++;
	    	ascCnt++;
        }
	}
	return ascCnt;
}

function RTrimByte(s, maxBytes) {
	var ascCnt = 0;
	var retStr = "";
	for (var n = 0; n < s.length; n++) {
		var c = s.charCodeAt(n);
        if (c <= 0x7F) {
			ascCnt += 1;
		} else {
	    	ascCnt += 2;
	    }
	    if (ascCnt > maxBytes) break;
		retStr += String.fromCharCode(c);
	}
	return retStr;
}

function addMaxLengthChecker(obj, maxlen) {
	if (obj != null) {
		obj.onkeypress = function aonoymous() {
			maxlimit = maxlen;
			ascLength = countBytes(obj.value);
			return (ascLength < maxlimit);
		};
		obj.onkeydown = function aonoymous() {
			maxlimit = maxlen;
			ascLength = countBytes(obj.value);
			if (ascLength >= maxlimit) {
				accept = false;
				accept |= event.keyCode == 8;  //backspace
				accept |= event.keyCode == 9;  //tab
				accept |= event.keyCode == 13; //enter
				accept |= event.keyCode == 16; //shift
				accept |= event.keyCode == 27; //esc
				accept |= event.keyCode == 37; //left
				accept |= event.keyCode == 38; //up
				accept |= event.keyCode == 39; //right
				accept |= event.keyCode == 40; //down
				accept |= event.keyCode == 46; //del
				if (!accept) return false;
				return true;
			}
		};
		obj.onblur = function aonoymous() {
			maxlimit = maxlen;
			ascLength = countBytes(obj.value);
			if (ascLength > maxlimit) {
				this.select();
				alert('Input is exceed');
			}
		};
		obj.onpaste = function aonoymous() {
			maxlimit = maxlen;
			ascLength = countBytes(obj.value);
			window.clipboardData.setData("Text", RTrimByte(window.clipboardData.getData("Text"), maxlimit - ascLength));
			event.returnValue = true;
		};
	}
}

function multiSelectedForJmesa(chkboxPreId,chkboxSufId,selectedStrsId){	
	var rname=chkboxPreId+chkboxSufId;	
	var str=$("#"+selectedStrsId).val();
	if($("input[name='"+rname+"']").prop("checked")){
        str+=chkboxSufId+",";
    } else{
    	var strs=str.substring(0,str.length-1);
    	var strsArray=strs.split(",");
    	var str2="";  	
    	for(var i=0;i<strsArray.length;i++){
    		if(strsArray[i]!=chkboxSufId){
    			str2+=strsArray[i]+",";
    		}
    	}    	
    	str=str2;   	
	}
	$("#"+selectedStrsId).val(str);
}
function multiSelectedAssignmentForJmesa(chkboxPreId,selectedStrs){	
	if(""==selectedStrs){
		$("input[type='checkbox']").removeAttr("checked"); 
	}else{
		var strsub=selectedStrs.substring(0,selectedStrs.length-1);
		var strsArray=strsub.split(",");	
		for(var i=0;i<strsArray.length;i++){
			var rname=chkboxPreId+strsArray[i];	
			if($("#"+rname).length>0){		
				$("input[name='"+rname+"']").attr("checked","true");
			}
		}  
	}
}
//add by elina
function selectedOnePageAssignmentForJmesa(chkboxPreId,selectedStrs,selectedhidRpId,selectedhidBatId,selectedhidEmpId){	
	if(""!=selectedhidRpId || null!=selectedhidRpId){
		$("#"+selectedhidRpId).val("");
	}
	if(""!=selectedhidBatId || null!=selectedhidBatId){
		$("#"+selectedhidBatId).val("");
	}
	if(""!=selectedhidEmpId || null!=selectedhidEmpId){
		$("#"+selectedhidEmpId).val("");
	}
	
	if(""==selectedStrs){
		$("input[type='checkbox']").removeAttr("checked"); 
	}else{
		var strsub=selectedStrs.substring(0,selectedStrs.length-1);
		var strsArray=strsub.split(",");	
		var reCheckedstr1="";
    	var reCheckedstr2="";
    	var reCheckedstr3="";
		for(var i=0;i<strsArray.length;i++){
			var rname=chkboxPreId+strsArray[i];	
			if($("#"+rname).length>0){		
				$("input[name='"+rname+"']").attr("checked","true");
				if(""!=selectedhidRpId || null!=selectedhidRpId){
					reCheckedstr1+=$("#"+strsArray[i]).val()+",";
				}
				if(""!=selectedhidBatId || null!=selectedhidBatId){
					reCheckedstr2+=$("#batSta"+strsArray[i]).val()+",";
				}
				if(""!=selectedhidEmpId || null!=selectedhidEmpId){
					reCheckedstr3+=$("#emp"+strsArray[i]).val()+",";
				}
			}
		}  
		if(""!=selectedhidRpId || null!=selectedhidRpId){
			$("#"+selectedhidRpId).val(reCheckedstr1);
		}
		if(""!=selectedhidBatId || null!=selectedhidBatId){
			$("#"+selectedhidBatId).val(reCheckedstr2);
		}
		if(""!=selectedhidEmpId || null!=selectedhidEmpId){
			$("#"+selectedhidEmpId).val(reCheckedstr3);
		}
	}
}
function multiSelectedForBatch(chkboxPreId,chkboxSufId,selectedStrsId,selectedhiddenStrsId){	
	var rname=chkboxPreId+chkboxSufId;	
	var str=$("#"+selectedStrsId).val();
	var hidStr = $("#"+selectedhiddenStrsId).val();
	if($("input[name='"+rname+"']").prop("checked")){
        str+=chkboxSufId+",";
        hidStr+=$("#"+chkboxSufId).val()+",";
    } else{
    	var strs=str.substring(0,str.length-1);
    	var strsArray=strs.split(",");
    	var str2="";  
    	var hidstr2="";
    	for(var i=0;i<strsArray.length;i++){
    		if(strsArray[i]!=chkboxSufId){
    			str2+=strsArray[i]+",";
    			hidstr2+=$("#"+strsArray[i]).val()+",";
    		}
    	}    	
    	str=str2;   
    	hidStr=hidstr2;
	}
	$("#"+selectedStrsId).val(str);
	$("#"+selectedhiddenStrsId).val(hidStr);
}

function multiSelectedForAppraisee(chkboxPreId,chkboxSufId,selectedStrsId,selectedhidRpId,selectedhidBatId,selectedhidEmpId){	
	var rname=chkboxPreId+chkboxSufId;	
	var str=$("#"+selectedStrsId).val();
	var hidStr1 = $("#"+selectedhidRpId).val();
	var hidStr2 = $("#"+selectedhidBatId).val();
	var hidStr3 = $("#"+selectedhidEmpId).val();
	if($("input[name='"+rname+"']").prop("checked")){
        str+=chkboxSufId+",";
        hidStr1+=$("#"+chkboxSufId).val()+",";
        hidStr2+=$("#batSta"+chkboxSufId).val()+",";
        hidStr3+=$("#emp"+chkboxSufId).val()+",";
    } else{
    	var strs=str.substring(0,str.length-1);
    	var strsArray=strs.split(",");
    	var str2="";  
    	var reCheckedstr1="";
    	var reCheckedstr2="";
    	var reCheckedstr3="";
    	for(var i=0;i<strsArray.length;i++){
    		if(strsArray[i]!=chkboxSufId){
    			str2+=strsArray[i]+",";
    			reCheckedstr1+=$("#"+strsArray[i]).val()+",";
    			reCheckedstr2+=$("#batSta"+strsArray[i]).val()+",";
    			reCheckedstr3+=$("#emp"+strsArray[i]).val()+",";
    		}
    	}    	
    	str=str2;   
    	hidStr1=reCheckedstr1;
    	hidStr2=reCheckedstr2;
    	hidStr3=reCheckedstr3;
	}
	$("#"+selectedStrsId).val(str);
	$("#"+selectedhidRpId).val(hidStr1);
	$("#"+selectedhidBatId).val(hidStr2);
	$("#"+selectedhidEmpId).val(hidStr3);
}

// For WebSEAL reverse proxy
function getJunction(contextPath){
    var url = window.location.pathname;
    var substr = url.indexOf(contextPath);
    if (substr > -1){
        url = url.substr(0,substr);
    } else {
        url = "";
    }
    return url;
}
//end
