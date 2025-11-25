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
  this._oBody.appendChild(this._oHelperIframe);

  this._oLayerDiv.style.border = 0;
  this._oLayerDiv.width = 0;
  this._oLayerDiv.height = 0;
  this._oLayerDiv.style.position = "absolute";
  this._oBody.appendChild(this._oLayerDiv);
  
  // Should return the div actual width.
  this._getLayerDivWidth = function(){
    // We are checking the inner table because of a bug in NS/Mozilla with the DIV-->offsetWidth
    var tableWidth = "" + this._oLayerDiv.getElementsByTagName("table").item(0).offsetWidth;
    if(tableWidth.indexOf('px') > -1){
      return parseInt(tableWidth.substring(0, tableWidth.infexOf('px')));
    } else {
      return tableWidth;
    }
  }
  
  // Should return the div actual Height.
  this._getLayerDivHeight = function(){
    // We are checking the inner table because of a bug in NS/Mozilla with the DIV-->offsetHeight
    var tableHeight = "" + this._oLayerDiv.getElementsByTagName("table").item(0).offsetHeight;
    if(tableHeight.indexOf('px') > -1){
      return parseInt(tableHeight.substring(0, tableHeight.infexOf('px')));
    } else {
      return tableHeight;
    }
  }

  this._attachToEvent = function(obj, name, func) {
    name = name.toLowerCase();
    // Add the hookup for the event.
    if(typeof(obj.addEventListener) != "undefined") {
      if(name.length > 2 && name.indexOf("on") == 0) name = name.substring(2, name.length);
      obj.addEventListener(name, func, false);
    } else if(typeof(obj.attachEvent) != "undefined"){
      obj.attachEvent(name, func);
    } else {
      if(eval("obj." + name) != null){
        // Save whatever defined in the event
        var oldOnEvents = eval("obj." + name);
        eval("obj." + name) = function(e) {
          try{
            func(e);
            eval(oldOnEvents);
          } catch(e){
          }
        };
      } else {
        eval("obj." + name) = func;
      }
    }
  }
  
  // Will move the div and the helper iframe to the given X and Y position
  this.moveTo = function(xPos, yPos){
    // Set the Y position
    if(yPos > Math.round(this._oBody.clientHeight / 2)){
      // Open to top
      this._oHelperIframe.style.top = yPos - this._getLayerDivHeight() + this._oBody.scrollTop; 
    } else {
      // Open to bottom
      this._oHelperIframe.style.top = yPos + this._oBody.scrollTop;
    }
  
    // Set the X position
    if(xPos > Math.round(this._oBody.clientWidth / 2)){
      // Open to left
      this._oHelperIframe.style.left = xPos - this.getLayerDivWidth() + this._oBody.scrollLeft; 
    } else {
      // Open to right
      this._oHelperIframe.style.left = xPos + 5 + this._oBody.scrollLeft; 
    }
    
    this._oLayerDiv.style.top = this._oHelperIframe.style.top;
    this._oLayerDiv.style.left = this._oHelperIframe.style.left;
    this.show();
  }

  this.show = function(){
    this._oHelperIframe.style.zIndex = topZIndex++;
    this._oLayerDiv.style.zIndex = topZIndex++;
    this.visibility = 'show';
    this._oHelperIframe.style.visibility = 'visible';
    this._oLayerDiv.style.visibility = 'visible';
  }

  this.hide = function(){
    this.visibility = 'hidden';
    this._oHelperIframe.style.visibility = 'hidden';
    this._oLayerDiv.style.visibility = 'hidden';
  }
  
  this.setContent = function(msg){
//    var divContent = "<table style='border:1px solid black;background-color:LightGoldenrodYellow' cellspacing='0' cellpading='0'><tr><td>" + msg + "</td></tr></table>";
    var divContent = msg;
    this._oLayerDiv.innerHTML = divContent;
    this.refresh();
  }

  this.refresh = function(){
    this._oHelperIframe.style.top = this._oLayerDiv.style.top;
    this._oHelperIframe.style.left = this._oLayerDiv.style.left;
    this._oHelperIframe.width = this._getLayerDivWidth();
    this._oHelperIframe.height = this._getLayerDivHeight();
  }
}
