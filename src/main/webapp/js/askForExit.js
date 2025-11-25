   function onUrlchange() {
         window.event.returnValue= "Closing this window may lose unsaved data.\nAre you sure to Exit?"
         window.event.cancelBubble=true;
   }
   //usage:
   //window.onbeforeunload = onUrlchange;