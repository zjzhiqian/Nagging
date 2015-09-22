<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%> 
<body>
	
	
	<div class="easyui-layout" data-options="fit:true"> 
		<div  data-options="region:'north'" style="height: 55px;">
			<div style="margin-top: 10px;margin-left: 100px">
				角色名称:
				<input type="text" name="authorityname"  style="width:200px;" data-options="required:true" readonly="readonly" value="${sysrole.authorityname }"/>
			
				角色描述:
				<input type="text" name="description" style="width:200px;" readonly="readonly" value="${sysrole.description }"/>
			</div>
		</div>
		<div data-options="region:'center'">
				<ul id="role-tree" data-options="required:true">
				</ul>
				<div style="margin-left: 150px">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="editRolePermission()">保存</a>
				</div>
		</div>
	</div>
	
	
	
	
	
	
	<script type="text/javascript">
		var role_tree;
		function editRolePermission(){
			
			
			
			var nodes=role_tree.tree('getChecked');
			var idArray=[];
			for(var i=0;i<nodes.length;i++){
				idArray.push(nodes[i].id);
			}
			var ids=idArray.join(',');
			loading('修改中');
			var roleid="${sysrole.id}";
			$.ajax({
				url : '${ctx}/system/rolePermissions/'+roleid,
				data:{'idArr':ids},
				success:function(r){
					loaded();
					 if(r&&r.flag){
						 showmsg(r.msg);
						 $("#role-tree").tree('reload')
					 }else{
						 alerterror(r.msg);
					 }
				 }
			})
		}
		$(function(){
		  role_tree=$("#role-tree").tree({
				width:0,
				method:'GET',
				url : '${ctx}/system/rolePermissions/${sysrole.id}',
				lines : true,
				multiple : true,
				/* panelHeight:300,
				panelWidth:300, */
				cascadeCheck:false,
				checkbox:true,
				onLoadSuccess : function(node, data) {
					role_tree.tree('expandAll',node);
					parent.$.messager.progress('close');
				},
			})
			
		})
		
		
	
	</script>

</body>