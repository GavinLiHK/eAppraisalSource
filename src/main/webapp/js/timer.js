var time=0; 
var TIMEOUT=0;
var _MESSAGE = "Your session is going to be timeout.\n Do you want to refresh your session?" ;
var WARNING = "Your session is going to be expired. Please save all your changes for preventing lossing data!" ;
var SID="";
var RATIO= 0.8;
var autoRenew = false ;
var timerId = -1;

function countDown(sid, interval) {
	init(sid, interval, false) ;
}

function autoRenewSession(sid, interval) {
	init(sid, interval, true) ;
}

function init(sid, interval, auto) {
	TIMEOUT=parseInt(interval * 1000);
	TIMEOUT=parseInt(TIMEOUT*RATIO);
	SID=sid;
	autoRenew = auto;
	startTimer();
}

function startTimer() {
	if (timerId != -1) {
	  clearTimeout(timerId);
//	  alert("timer " + timerId + " cleared");
	}
	timerId = setTimeout("renewSession()", TIMEOUT);
//  window.status = "timer reset " + (new Date()).toString() + ", interval:" + TIMEOUT + " ms, timer id: " + timerId;
}

function renewSession() {
  if (autoRenew) {
    if (window.opener && !window.opener.closed) {
      window.opener.document.forms[0].submit();
    } else {
      alert(WARNING) ;
    }
    
    startTimer();
  } else if (confirm(_MESSAGE)) {
    document.forms[0].submit();
  }
}