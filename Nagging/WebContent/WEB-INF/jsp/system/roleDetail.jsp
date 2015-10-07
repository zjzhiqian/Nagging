<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<body>


	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" style="height: 55px;">
			<div style="margin-top: 10px; margin-left: 100px">
				角色名称: <input type="text" name="authorityname" style="width: 200px;" data-options="required:true" readonly="readonly" value="${sysrole.authorityname }" /> 角色描述: <input type="text" name="description" style="width: 200px;" readonly="readonly" value="${sysrole.description }" />
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
				cascadeCheck:false,
				checkbox:true,
				onLoadSuccess : function(node, data) {
					role_tree.tree('expandAll',node);
					parent.$.messager.progress('close');
				},
				onCheck:function(node,checked){
					if(checked){
						var isLeaf=role_tree.tree('isLeaf',node.target);
						if(isLeaf){
							var pnode=role_tree.tree('getParent',node.target);
							if(pnode){
								role_tree.tree('check',pnode.target);
							}
						}else{
							var cnode=role_tree.tree('getChildren',node.target);
							if(cnode&&cnode.length>0){
								
								for(var i=0;i<cnode.length;i++){
									var isLeafc=role_tree.tree('isLeaf',cnode[i].target);
									if(!isLeafc){
										console.log(cnode[i])
										role_tree.tree('check',cnode[i].target);
									}
								}
							}
						}

					}else{
						//取消选中时的动作
						var cnodes=role_tree.tree('getChildren',node.target);
						for(var i=0;i<cnodes.length;i++){
							role_tree.tree('uncheck',cnodes[i].target); 
						}
						
					}
				}
				
			})
			
		})
		
		
	
	</script>

</body>