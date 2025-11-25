// BOI, followed by one or more digits, followed by EOI.
var reInteger = /^\d+$/

function viewScheduleApplet(channel,schedule_date,start_time){
    
    var msgWindow=window.open("viewScheduleApplet.jsp?channel_id="+channel+"&schedule_date="+schedule_date+"&start_time="+start_time);
}


function viewExcel(sessionKey,http_root){
    var msgWindow=window.open("http://localhost/asaps/Jsp/weeksch/viewExcel.jsp"
		+"?SESSION_KEY="+sessionKey
		,"Excel","top=0,left=0,width=100,height=50");
    msgWindow.focus();
}

function viewPrintable(http_root){
    var msgWindow=window.open(http_root+"/viewPrintable.jsp"
		,"Printable","top=0,left=0,width=800,height=600,scrollbar=y");
    msgWindow.focus();
}


function refresh(refreshForm){
    refreshForm.submit();
}


function copy_clipboard(){
    	//holdtext.innerText = copytext.innerText;
	Copied = idList.createTextRange();
	Copied.execCommand("Copy");
}
function paste_clipboard(){
	
	return window.clipboardData.getData("Text");
}

function myOnError(msg, url, lno) {
        msgArray[msgArray.length] = msg
        urlArray[urlArray.length] = url
        lnoArray[lnoArray.length] = lno
        alert(navigator.appName+" "+navigator.appVersion+" doesn't support this function! "+msg);
        return true       
        
}            
    
function printOut(){
        window.onerror = myOnError
        msgArray = new Array()
        urlArray = new Array()
        lnoArray = new Array()
        window.print();
}

function checkSingle(cbPrefix,cb){

    if (!cb.checked) return;
    var theForm = cb.form;
    var cbName = cb.name;

    var pos = cbName.replace(cbPrefix,"");
    var xIndex = pos.replace(/_.*$/,"");  // replace all chars start from "_" to end of string;
    var yIndex = pos.replace(/^.*_/,"");  // replace all chars start from begining to  "_"

    if (!isInteger(xIndex)) return false;
    if (!isInteger(xIndex)) return false;

    var count = 0;
    for (i=0 ;i<4 ;i++)
    {
		theForm.elements(cbPrefix+i+"_"+yIndex).checked = false;
    }
	cb.checked = true;

//Comment by Silas on 2/8/04
/*
    if (!cb.checked) return;
    var theForm = cb.form;
    var cbName = cb.name;

    var pos = cbName.replace(cbPrefix,"");
    var xIndex = pos.replace(/_.*$/,"");  // replace all chars start from "_" to end of string;
    var yIndex = pos.replace(/^.*_/,"");  // replace all chars start from begining to  "_"

    if (!isInteger(xIndex)) return false;
    if (!isInteger(xIndex)) return false;

    var count = 0;

    for (i = 0 ;;i++)
    {
        if (!theForm.elements(cbPrefix+i+"_"+yIndex)){

            return true;
        }
        else{
            if (theForm.elements(cbPrefix+i+"_"+yIndex).checked){
                theForm.elements(cbPrefix+i+"_"+yIndex).checked = false;
			}
             if (count > 1)
             {
                alert('Only one checkbox can be selected');

                cb.checked = false;
                return false;
             }
        }
    }
*/    
}


function isInteger (s)

{   var i;

    if (isEmpty(s)) 
       if (isInteger.arguments.length == 1) return defaultEmptyOK;
       else return (isInteger.arguments[1] == true);
       
    return reInteger.test(s)
}


function isEmpty(s)
{   return ((s == null) || (s.length == 0))
}

function myOnError(msg, url, lno) {
        msgArray[msgArray.length] = msg
        urlArray[urlArray.length] = url
        lnoArray[lnoArray.length] = lno
        alert(navigator.appName+" "+navigator.appVersion+" doesn't support this function! "+msg);
        return true       
        
}            
    
function printOut(){
        window.onerror = myOnError
        msgArray = new Array()
        urlArray = new Array()
        lnoArray = new Array()
        window.print();
}


// for save filter for search weekly schedule page

var cookieName = "asaps_weeksch" ;
var separator = "||" ;

// save search criteria as a pre-defined filter
function saveToFilter(filterName, f,channel, date, printexcel,fontSize,slotDuration) {
  if (filterName!=null) {
    if (isEmpty(filterName)) {
      alert("Please enter the filter name") ;
      return false ;
    }
    else {
      var tempArray ;
      var info = getCookie(cookieName) ;
      if (info != null) {
        tempArray = info.split(separator) ;
        info = "" ;
        var filName = "" ;
        for (i=0; i<tempArray.length; i++) {
          filName = splitString(tempArray[i], "filterName=", "&") ;
          if (filName.toUpperCase() != filterName.toUpperCase()) {
            info += tempArray[i] + separator ;
          }
        }
      }
      else {
        info = "" ;
      }
      info += "filterName=" + filterName ;


      info += "&channel_id=" + channel.value ;
      info += "&date=" + date.value ;

      if (printexcel.checked)
      info += "&printexcel=" + printexcel.value ;
      else
      info += "&printexcel=";

     
	  
	  
	  
	   info += "&font_size=" + fontSize.value ;
	  info += "&slot_duration=" + slotDuration.value ;
	  
	  
	  
	  var selected_choice ;
      var cbname;
      selected_choice="";
      for (i=0;i<6;i++) {//change from 7 to 6 for bug fix(3-11-2004 Leo Chu)
	for (j=0;j<4;j++) {
	    cbname="pgm_title_choice0_sel"+j+"_"+i+"";
		if (f.elements(cbname).checked) {
		   selected_choice+=cbname+",";
		}
	}
      }
      info += "&choice="+selected_choice;
	
      var selected_legends ;
      selected_legends = "";
      var selected_ppvs ;
      selected_ppvs = "";


      for (var i = 0 ; i < document.pageForm.elements('legends_sel_ASSIGN').options.length; i++)
      {
        selected_legends+=f.elements('legends_sel_ASSIGN').options[i].value+",";
      }
      for (var i = 0 ; i < document.pageForm.elements('ppvs_sel_ASSIGN').options.length; i++)
      {
        selected_ppvs+=f.elements('ppvs_sel_ASSIGN').options[i].value+",";
      }
      
      info += "&selected_legends="+selected_legends;
      info += "&selected_ppvs="+selected_ppvs;

      setCookie(cookieName, info) ;
      
      return true ;
    }
  }
  return false ;
}


// check the value is empty or not
function isEmpty(value) {
  var temp = value.replace(/[' ']/g, '') ;
  if (temp.length == 0) 
    return true ;
  else 
    return false ;
}


// set the cookie
function setCookie(cookieName, info) {
  var day = 24 * 3600000 ;
  var expire = new Date((new Date()).getTime() + (365*day)) ;
  document.cookie = cookieName + "=" + info ;
  document.cookie += "; expires=" + expire.toGMTString() ;
}


// get the cookie
function getCookie(cookieName) {
  var ckie = document.cookie ;
  return splitString(ckie, cookieName+"=", ";") ;
}


// load the search criteria from pre-defined filter
function loadPredefinedFiltersFromCookie(predefinedFilter) {
  var ckie = getCookie(cookieName) ;
  var name = "--- Please Select ---" ;
  var opt = new Option(name, name) ;
  predefinedFilter.options[0] = opt ;
  if (ckie != null) {
    var tempArray = ckie.split(separator) ;
    for (i=0; i<tempArray.length; i++) {
      name = splitString(tempArray[i], "filterName=", "&") ;
      opt = new Option(name, name) ;
      predefinedFilter.options[i+1] = opt ;
    }
  }
}


// load criteria from filter

function loadCriteriaFromFilter(predefinedFilter, f, channel, date, printexcel) {
  var ckie = getCookie(cookieName) ;
  var tempArray = ckie.split(separator) ;
  for (k=0; k<tempArray.length; k++) {
    var name = splitString(tempArray[k], "filterName=", "&") ;
    var value = predefinedFilter.options[predefinedFilter.selectedIndex].value ;
    if (name.toUpperCase() == value.toUpperCase()) {
      var channel_id = splitString(tempArray[k], "channel_id=", "&") ;
      var date = splitString(tempArray[k], "date=", "&") ;
      var printexcel = splitString(tempArray[k], "printexcel=", "&") ;
      var choice = splitString(tempArray[k], "choice=", "&") ;
      var selected_legends = splitString(tempArray[k], "selected_legends=", "&") ;
      var selected_ppvs = splitString(tempArray[k], "selected_ppvs=", "&") ;
	   var fontSize = splitString(tempArray[k], "font_size=", "&") ;
      var slotDuration = splitString(tempArray[k], "slot_duration=", "&") ;


      if (channel_id != null)
        document.pageForm.htmlPageTopContainer_pageForm_channel_id.value = channel_id ;
      else 
        document.pageForm.htmlPageTopContainer_pageForm_channel_id.value = "" ;
      if (date != null)
        document.pageForm.htmlPageTopContainer_pageForm_date.value = date ;
      else 
        document.pageForm.htmlPageTopContainer_pageForm_date.value = "" ;

      if (printexcel == 'Y')
        document.pageForm.printExcel.checked = true ;
      else 
        document.pageForm.printExcel.checked = false ;

    
	  
	    if (fontSize != null)
        document.pageForm.font_size.value = fontSize ;
      else 
        document.pageForm.font_size.value = "" ;



	   if (slotDuration != null)
        document.pageForm.slot_duration.value = slotDuration ;
      else 
        document.pageForm.slot_duration.value = "" ;
	  
	  
	  
	  
	  for (i=0;i<6;i++) {
	for (j=0;j<4;j++) {
	    cbname="pgm_title_choice0_sel"+j+"_"+i+"";
	    document.pageForm.elements(cbname).checked=false;
	}
      }
      var chkboxes = choice.split(",") ;
	for (i=0;i<chkboxes.length;i++) {
	   if (chkboxes[i]!='')
	   document.pageForm.elements(chkboxes[i]).checked = true ;
	}

      //moveSelect('legends_sel_UNASSIGN','legends_sel_ASSIGN',true);
      //moveSelect('ppvs_sel_UNASSIGN','ppvs_sel_ASSIGN',true);
      var legends = selected_legends.split(",") ;
      
//By Jack to reset the selection 
var m =0;
processAssign('delAll',f,'legends_sel');
//By Jack end

	for (m=0;m<legends.length-1;m++) {
	   
	   
	   if (legends[m]!='') {
	   for (j=0;j<document.pageForm.legends_sel_UNASSIGN.length;j++)
	   { 
		if (document.pageForm.legends_sel_UNASSIGN.options[j].value==legends[m]) {
		  document.pageForm.legends_sel_UNASSIGN.options(j).selected = true;
		}
	   }
	
	   processAssign('add',f,'legends_sel');
	 
	   }

	}

  
	  var ppvs = selected_ppvs.split(",") ;
  
  //By Jack to reset the selection
  processAssign('delAll',f,'ppvs_sel'); 
  //By Jack end

	for (m=0;m<ppvs.length-1;m++) {
	   if (ppvs[i]!='') {
	   for (j=0;j<document.pageForm.ppvs_sel_UNASSIGN.length;j++)
	   { 
		if (document.pageForm.ppvs_sel_UNASSIGN.options[j].value==ppvs[m]) {
		  document.pageForm.ppvs_sel_UNASSIGN.options(j).selected = true;
		}
	   }
	
	   processAssign('add',f,'ppvs_sel');
	   
	   }
	}

    }
  }
}


// return a specific string between offsetStr and endStr
function splitString(str, offsetStr, endStr) {
  var index = str.indexOf(offsetStr);
  if (index == -1) 
    return null;
  index = str.indexOf("=", index) + 1;
  var endstr = str.indexOf(endStr, index);
  if (endstr == -1) 
    endstr = str.length;
  return str.substring(index, endstr);
}


// end for save filter for search weekly schedule page



// assignment boxes js functions

function moveSelect(select1,select2,isAll) // move from select 2 to select 1
{
    select2Options = select2.options;
    for (i = 0 ; i < select2Options.length;i++){
        if ((select2Options[i].selected)||(isAll))
        {
            select1.options[select1.length]=
                new Option(
                    select2[i].text,select2[i].value
                );
            select2[i] = null;
            i--; // as select2.length change , adjust i for correct indexing
            if (!isAll)
                break;
        }
    }
}

function moveSelectWithCount(select1,select2,count) // move from select 2 to select 1
{
    select2Options = select2.options;
    for (i = 0 ; i < select2Options.length;i++){
        if ((select2Options[i].selected)||(count > 0 ))
        {
            select1.options[select1.length]=
                new Option(
                    select2[i].text,select2[i].value
                );
            select2[i] = null;
            i--; // as select2.length change , adjust i for correct indexing
            count --;
        }
    }
}


function processAssign(cmd,f,namePrefix){_processAssign(cmd,f,namePrefix,-1);}
function processAssign(cmd,f,namePrefix,maxAssigned){_processAssign(cmd,f,namePrefix,maxAssigned);}

function _processAssign(cmd,f,namePrefix,maxAssigned)
{
    assignedSelect = f.elements[namePrefix+'_ASSIGN'];
    unassignSelect = f.elements[namePrefix+'_UNASSIGN'];
    
    if ((cmd=="add"||cmd=="addAll")&&(maxAssigned>0)&&(assignedSelect.length == maxAssigned)){
        alert("Max Reached");
        return;
    }

    if (cmd=="add"){
        if (unassignSelect.selectedIndex!=-1)
            moveSelect(assignedSelect,unassignSelect,false);
	    selectAllAssigned(f,namePrefix);
    }else if (cmd=="addAll"){
        if (maxAssigned>0){
            availSpace = maxAssigned - assignedSelect.options.length;
            moveSelectWithCount(assignedSelect,unassignSelect,availSpace);
        }else{
            moveSelect(assignedSelect,unassignSelect,true);
        }
	selectAllAssigned(f,namePrefix);
    }else if (cmd=="del"){
        if (assignedSelect.selectedIndex!=-1)
            moveSelect(unassignSelect,assignedSelect,false);
    }else if (cmd=="delAll"){
        moveSelect(unassignSelect,assignedSelect,true);
    }
    selectAllAssigned(f,namePrefix);
    
}

function selectAllAssigned(f,namePrefix)
{
    for (var i = 0 ; i < f.elements[namePrefix+'_ASSIGN'].options.length; i++)
    {
        f.elements[namePrefix+'_ASSIGN'].options[i].selected= true;
    }
}

function isNoneAssigned(f,namePrefix){
    return (f.elements[namePrefix+'_ASSIGN'].options.length==0);
}

function validateForm(f,namePrefix,elename){
    selectAllAssigned(f,namePrefix);
    return true;
}

var newwin ; 

function openApplet(target, parms, name, h, w, s, t, m) {
  if (newwin != null) 
    newwin.close() ;
  newwin = window.open(target + parms, name, 'height=' + h + ',width=' + w + ',status=' + s + ',toolbar=' + t + ',menubar=' + m);
}
function openApplet2(target, parms, name, h, w, s, t, m,d) {
	opener.focus();
	opener.document.applets['htmlPageTopContainer_pageForm_box1_ProgrammeScheduler'].changeDate(d);


}