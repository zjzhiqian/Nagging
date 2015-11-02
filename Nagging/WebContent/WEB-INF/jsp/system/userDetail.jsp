<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<body>
<script>


$(function(){
	 $('#roleIds').combotree({
			url : '${ctx}/system/comborole',
			lines : true,
			multiple : true,
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			},
			cascadeCheck : false,
			value : $.stringToList('${edituser.roleIds}'),
	});  
	$('#roleIds').combotree('showPanel');
	
	
	$('#btn-auth').click(function(){
		var idArray=$('#roleIds').combotree('getValues');
		var ids=idArray.join(',');
		loading('授权中')
		$.ajax({
			url:'${ctx}/system/authorization/'+"${edituser.id}",
			data:{'ids':ids},
			success:function(r){
			 	 loaded();
				 if(r&&r.flag){
					 showmsg(r.msg);
					 parent.$.modalDialog.dg.datagrid('reload');
					 parent.$.modalDialog.handler.dialog('close');
				 }else{
					 alerterror(r.msg)
				 }
			 }
			
		})
	})
	
	
})


</script>




<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post" align="center">
			<input id="editId" name="id" type="hidden" value="${edituser.id}">
			<input id="hiddenIds" name="hiddenIds" type="hidden">
			
			<div style="margin-top: 10px">
				<label>&nbsp;用户名:</label>
				<input class="easyui-textbox" type="text" value="${edituser.username}"  readonly="readonly" style="width: 200"/>
			</div>
			<div style="margin-top: 10px">
				<label>勾选角色:</label>
				<input id="roleIds" style="width: 200px "></input>
			</div>
		</form>
	</div>
</div>



</body>