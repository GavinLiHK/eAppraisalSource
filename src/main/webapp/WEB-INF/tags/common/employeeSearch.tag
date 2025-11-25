<%@ include file="/common/taglibs.jsp"%>
<%@ attribute name="empNameId" type="java.lang.String" required="true"%>
<%@ attribute name="empName" type="java.lang.String" required="true"%>
<%@ attribute name="empNumId" type="java.lang.String" required="true"%>
<%@ attribute name="empNum" type="java.lang.String" required="true"%>
<%@ attribute name="empNameIdReadOnly" type="java.lang.String" required="false"%>
<%@ attribute name="searchBtnHidden" type="java.lang.String" required="false"%>
<%@ attribute name="empNameIdHidden" type="java.lang.String" required="false"%>
<%@ attribute name="empNumHidden" type="java.lang.String" required="false"%>
<%@ attribute name="disabled" type="java.lang.String" required="false"%>

<%-- <form:input id="${empNumId}" name="${empNumId}" readonly="true" value="" classname="inputfield" size="15" maxlength="10" path="${empNum}" disabled="${disabled}" style="display:${empNumHidden}"/> --%>
<form:input id="${empNumId}" name="${empNumId}" value="" classname="inputfield" size="15" maxlength="10" path="${empNum}" disabled="${disabled}" style="display:${empNumHidden}"  onkeypress="handle(event,'${empNumId}','${empNameId}')" />
<c:if test="${empNumHidden !='none'}">
&nbsp;&nbsp;
</c:if>
<form:input id="${empNameId}" name="${empNameId}" readonly="${empNameIdReadOnly}" path="${empName}" classname="editableinputfield" size="40" style="display:${empNameIdHidden }" disabled="${disabled}" onkeypress="handle(event,'${empNumId}','${empNameId}')" />
<c:choose>
<c:when test="${searchBtnHidden!='Y'}">
<input type="button" id="${empNumId}btnEmpSearch" name="${empNumId}btnEmpSearch" value="Search" onclick="empSearch('${empNumId}','${empNameId}');"/>
</c:when>
</c:choose>

<script>
 function handle(event, pnum, pname){
	 if(event.keyCode == 13){
		 empSearch(pnum,pname);
	 }
 }
 
 function empSearch(pnum,pname){
	console.log("b:"+pnum);
	var num=$.trim($("#"+pnum).val());
	console.log("num:"+num);
	console.log("b:"+pname);
	//20161228 added search employee name
	var name = encodeURIComponent($("#"+pname).val());
	if((null==num||""==num)&&(null==name||""==name)){
		window.open(getJunction('/ea/')+"${ctx}/common/EmployeeEnquiry.do?pidNum="+pnum+"&pidName="+pname+"&initPage=","window","width:600px,height=400px,toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
	}else if(name!=null&&name!=""){
		//window.open(getJunction('/ea/')+"${ctx}/common/EmployeeEnquiry.do?pidNum="+pnum+"&pidName="+pname+"&pidEmpName="+name+"&initPage=","window","width:600px,height=400px,toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
		searchEmployee(pname,pnum,name,num);
	}else{
		searchEmployeeName(num,pname);
	}
}
//20170106 updated by joanna for search employee by name
function searchEmployee(pname,pnum,name,num){
	var id="searchMulti";
	var url =  getJunction('/ea/')+"${ctx}/common/searchNoOfEmployee.do?name="+name+"&pid="+id;
	$.ajax({
		url: url,
		type: "get",
       // data: {num:num},
        //dataType: "json",
        async: false,
        ContentType: "application/json",
        cache: true,
        success: function(data){
        		if(data == '1'){
        			searchEmployeeNumberByName(pname,pnum,name,num);
        		}else{
        			window.open(getJunction('/ea/')+"${ctx}/common/EmployeeEnquiry.do?pidNum="+pnum+"&pidName="+pname+"&pidEmpName="+name+"&initPage=","window","width:600px,height=400px,toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
        		}
                 },
        error:function(error){
	    	//alert("Ajax Error" + error);
	    	console.log(error);
	    },
    });
}
function searchEmployeeNumberByName(pname,pnum,name,num){
	var id = "searchSingle";
	var url =  getJunction('/ea/')+"${ctx}/common/searchNoOfEmployee.do?name="+name+"&pid="+id;
	$.ajax({
		url: url,
		type: "get",
       // data: {num:num},
        //dataType: "json",
        async: false,
        ContentType: "application/json",
        cache: true,
        success: function(data){
        	$("#"+pnum).val(data);
        	searchEmployeeName(data,pname);
                 },
        error:function(error){
	    	//alert("Ajax Error" + error);
	    	console.log(error);
	    },
    });
}
function searchEmployeeName(num,pname){
	var url =  getJunction('/ea/')+"${ctx}/common/searchEmployeeName.do?num="+num;
	$.ajax({
		url: url,
		type: "get",
       // data: {num:num},
        //dataType: "json",
        async: false,
        ContentType: "application/json",
        cache: true,
        success: function(data){
        	$("#"+pname).val(data);
                 },
        error:function(error){
	    	//alert("Ajax Error" + error);
	    	console.log(error);
	    },
    });
}
//20170106 ended by joanna for search employee by name
$("#${empNameId}").keyup(function(e){
	var num=$.trim($("#${empNumId}").val());
	var name=$.trim($("#${empNameId}").val());
	var code = e.keyCode || e.which;
	
	if (code != "9"){
		if(null==name||""==name){
			$("#${empNumId}").val("");
			//20220708 Clear employee name if the employee number is clear 
			console.log("empty id")
			//20220708 ended Clear employee name if the employee number is clear
		}else{
			//if(e.keyCode==13){
				//empSearch('${empNumId}','${empNameId}');
				//window.showModalDialog("${ctx}/common/EmployeeEnquiry.do?pidNum="+pnum+"&pidName="+pname,"window","dialogWidth:600px;DialogHeight=400px;status:no;scroll=no;help:no;resizable:no;toolbar=no; menubar=no;");
			//}
		}
	}	
	}); 
//20220708 Clear employee name if the employee number is clear
$("#${empNumId}").keyup(function(e){
	var num=$.trim($("#${empNumId}").val());
	var name=$.trim($("#${empNameId}").val());
	if(null==num||""==num){
		console.log("null num condition")
		$("#${empNameId}").val("");
		console.log("empty name")
	}
});
//20220708 ended Clear employee name if the employee number is clear
</script>