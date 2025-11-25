<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="com.hkha.ea.common.Constants" %>
<%@ attribute name="msg" type="java.lang.String" required="false"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="parentId" type="java.lang.String" required="true"%>
<div id="${id}" title="HA-EA">
<c:choose>   
   <c:when test="${msg!=null}">   
	<!-- <p>${msg}</p>  -->
   </c:when>    
   <c:otherwise>  
	<!-- <p>This action will override the existing content.  Are you sure to proceed?</p>  -->
   </c:otherwise>  
</c:choose>  
 
</div>

<script>
$(document).ready(function() {		
	$("#${id}").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {
	       		$( this ).dialog( "close" );
	       		 OpenWindows('${parentId}');
	            	
	          },
	          "CANCEL":function(){
	        	  $( this ).dialog( "close" );
	          }}
	});
	
});
function OpenWindows(param){
	window.open(getJunction('/ea/')+"${ctx}/common/SearchMessage.do?pId="+param,"window","width:400px,height=600px,toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");	
}
</script>