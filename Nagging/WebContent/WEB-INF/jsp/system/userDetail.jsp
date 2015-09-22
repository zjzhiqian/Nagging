<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
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
		<form id="form" method="post" style="margin-left: 30px">
			<input id="editId" name="id" type="hidden" value="${edituser.id}">
			<input id="hiddenIds" name="hiddenIds" type="hidden">
			<table>
				<tr>
					<th>用户名</th>
					<td>
						<input type="text" class="span2" value="${edituser.username}" readonly="readonly" style="width: 200">
					</td>
				</tr>	
				<tr>		
					<th>勾选角色</th>
					<td>
						<select id="roleIds" style="width: 140px; height: 29px;"></select>
					</td>
				</tr>	
			</table>
		</form>
	</div>
</div>






</body>