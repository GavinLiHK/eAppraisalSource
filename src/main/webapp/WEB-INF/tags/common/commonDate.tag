<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="readonly" type="java.lang.String" required="false"%>
<%@ attribute name="disabled" type="java.lang.String" required="false"%>

<c:choose>
<c:when test="${readonly=='Y' || disabled == 'true'}">
	<form:input type="text" size="10" maxlength="10"  path="${name}" id="${id}readOnly"  readonly="true" disabled="${disabled}"/>
</c:when>
<c:otherwise>
  <form:input type="text" size="10" maxlength="10" class="datepickers" path="${name}" id="${id}"  />
</c:otherwise>
</c:choose>

 
 <script>
$(document).ready(function() {
	var dateConfig = { 
			showOn: 'both',               //二个都显示 
			buttonImage: getJunction('/ea/')+'${ctx}/images/cal.gif',  //加载图片 
			buttonImageOnly: true,        //显示图片的地方有一个突出部分，这个就是隐藏那玩意的 
			//changeFirstDay: false,        //这个参数干什么的呢，星期一是日历的第一个，不可以改动的 
			//numberOfMonths: 2,            //显示二个月，默认一个月 
			//minDate: nowdays,             //显示最小时间是今天 
			dateFormat: 'dd/mm/yy',       //日期格式 
		    //yearRange: '-20:+20'          //前后20年，不过这根minDate是今天有冲突，自己去试吧。 
			//buttonText:
			};  
	$("#${id}").datepicker(dateConfig);
	
	
});
//$('.datepickers').datepicker({dateFormat: "dd/mm/yyyy"});  
 </script>