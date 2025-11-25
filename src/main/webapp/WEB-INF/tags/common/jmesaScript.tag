<%@ include file="/common/taglibs.jsp"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ attribute name="ajax" required="false" type="java.lang.Boolean"%>
<%@ attribute name="refreshRegion" required="false" type="java.lang.String"%>
<%@ attribute name="actionFlagStr" required="false" type="java.lang.String"%>

<c:choose>
<c:when test="${ajax}">	
<div id="loadingBar" style="background:#FFFFEE;width:100px;"></div>
	<script type="text/javascript">
			function onInvokeAction(id){
				setExportToLimit(id, '');
				var divName ="#" +'${refreshRegion}'+"";
				var parameterString = createParameterStringForLimit(id);
			  	$('#loadingBar').show();
			  	$('#loadingBar').html("Loading......");
			  	$.ajax({url: '${action}?ajax=true&'+parameterString,
					type: 'GET',
					dataType: 'html',
					timeout: 80000,
					error: function(){alert('error');
					},
					success: function(html){
						// do something with xml
						//$('#'+"'"+${refreshRegion}+"'").html(html);
						var divName ="#" +'${refreshRegion}'+"";
						$(divName).html(html);
						$('#loadingBar').hide();
						//alert(html)
					}
				});//end of ajax
			}

			
			function onInvokeExportAction(id) {
				var parameterString = createParameterStringForLimit(id);
				location.href = '${action}?ajax=false&' + parameterString;
			}
	
			function toolBarImageOnClick(actionUrl){
				var form = document.getElementsByTagName("form")[0];
				form.action = actionUrl;
				form.submit();
			}
	
	</script>		
</c:when>
<c:otherwise>
	<script type="text/javascript">
	        function selectAllByTableId(id) {
		        var actionFlag = document.getElementsByName("actionFlag");
		        var actionFlagStr = '${actionFlagStr}';
		        if(actionFlag != 'undefined' && actionFlagStr != ""){
			        for(var i=0; i < actionFlag.length; i++){
				        actionFlag[i].value = '80';
				    }
				}

				setExportToLimit(id, '');
				createHiddenInputFieldsForLimitAndSubmit(id);
			}
			
			function onInvokeAction(id) {
				var actionFlag = document.getElementsByName("actionFlag");

				var actionFlagStr = '${actionFlagStr}';
				if(actionFlag != 'undefined' && actionFlagStr != ""){
					for(var i=0; i < actionFlag.length; i++){
						actionFlag[i].value = actionFlagStr;
					}
				}
				setExportToLimit(id, '');
				createHiddenInputFieldsForLimitAndSubmit(id);
			}
			function onInvokeExportAction(id) {
				var parameterString = createParameterStringForLimit(id);
				location.href = '${action}' + parameterString;
			}
			function toolBarImageOnClick(actionUrl){
				var form = document.getElementsByTagName("form")[0];
				form.action = actionUrl;
				form.submit();
			}			
	</script>
</c:otherwise>
</c:choose>
	<script type="text/javascript">
			
			function selectAll(id, rowcount, currentObj) {
				for(var i=1;i<=rowcount;i++){
					var rowCheckBox = document.getElementById(id+i);
					if(rowCheckBox!=null){
						rowCheckBox.checked = currentObj.checked;
					}else{
						//do nothing
					}
				}
			}
		
	</script>
