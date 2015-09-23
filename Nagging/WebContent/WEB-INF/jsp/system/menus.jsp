<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<body>

	<div class="easyui-panel" data-options="fit:true" style="padding: 2px;">


		<div class="easyui-layout" data-options="fit:true">

			<div title="" data-options="region:'west'" style="width: 500px">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'" style="height: 30px">
						<shiro:hasPermission name="menu:add">	
						 <a id="btn-add" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="height: 25px">新增</a>
						</shiro:hasPermission>
						 <shiro:hasPermission name="menu:edit">	
						<a id="btn-edit" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a> 
						 </shiro:hasPermission>
						<shiro:hasPermission name="menu:delete">	
						 <a id="btn-delete" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
						</shiro:hasPermission>
					</div>
					<div data-options="region:'center'">
						<div id="menu-tree" />
					</div>

				</div>
			</div>




		</div>


	</div>

	<script type="text/javascript">
		var menu_tree;
		$(function() {
			menu_tree = $("#menu-tree").tree({
				width : 0,
				method : 'POST',
				url : '${ctx}/system/menus',
				lines : true,
				multiple : true,
				onBeforeLoad : function(node, param) {
					if (menu_tree) {//加载Tree之前
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
					}
				},
				onLoadSuccess : function(node, data) {
					menu_tree.tree('expandAll', node);
					parent.$.messager.progress('close');
				},
				onClick : function(node) {
					if (node&&node.attributes && node.attributes.type) {
						var type = node.attributes.type;
						if (type == "1" || type == "0" || type == "2") {
							if (node.state == 'closed') {
								$(this).tree('expand', node.target);
							} else {
								$(this).tree('collapse', node.target);
							}
						} else if (type == "3") {
							//TODO 显示菜单详情
						}

					}
				},
			})

		})
	
		//新增
		$("#btn-add").click(function() {
			var node=menu_tree.tree("getSelected");
			console.log(node)
			if(node&&node.attributes && node.attributes.type){
				var id=node.id;
				var title="新增菜单";
				if(node.attributes.type=="2"){
					title="新增权限"
				}
				parent.$.modalDialog({
					href : "${ctx}/system/addmenu/"+id+"?text="+node.text+"&type="+node.attributes.type,
					title : title,
					cache : false,
					modal : true,
					width : 400,
					height : 280,
					onBeforeClose:function(){
						menu_tree.tree("reload")
					},
					buttons : [ {
						id : 'btn-menuAdd',
						text : '添加',
						width : 100,
						iconCls : 'icon-ok',
					} ]
				});
			}else{
				$.messager.confirm('请确认', '确定要增加一个顶级菜单吗', function(r){
					if (r){
						var node=menu_tree.tree("getSelected");
						parent.$.modalDialog({
							href : "${ctx}/system/addmenu/-1?text=无&type=-1",
							title : "新增顶级菜单",
							cache : false,
							modal : true,
							width : 400,
							height : 280,
							onBeforeClose:function(){
								menu_tree.tree("reload")
							},
							buttons : [ {
								id : 'btn-menuAdd',
								text : '添加',
								width : 100,
								iconCls : 'icon-ok',
							} ]
						});
					}
				});
			}

		})
		
		
		//删除
		$("#btn-delete").click(function(){
			var node=menu_tree.tree("getSelected");
			if(node!=null){
				$.messager.confirm('请确认', '确定删除此节点吗', function(r){
						if (r){
							var node=menu_tree.tree("getSelected");
							$.ajax({
								url:"${ctx}/system/delmenu/"+node.id,
								success:function(r){
									if(r&&r.flag){
										showmsg(r.msg)
										$("#menu-tree").tree('reload');
									}else{
										alerterror(r.msg);
									}
								}
							})
						}
					});
			}else{
				alertinfo("请选择一个节点")
			}
		})
		
		//修改
		$("#btn-edit").click(function() {
			var node=menu_tree.tree("getSelected");
			if(node==null){
				alertinfo("请选择节点");
			}else if(node&&node.id){
				var title="修改菜单";
				if(node.attributes.type=="2"){
					title="修改权限"
				}
				parent.$.modalDialog({
					href : "${ctx}/system/editmenu/"+node.id,
					title : title,
					cache : false,
					modal : true,
					width : 400,
					height : 280,
					onBeforeClose:function(){
						menu_tree.tree("reload")
					},
					buttons : [ {
						id : 'btn-menuEdit',
						text : '保存',
						width : 100,
						iconCls : 'icon-save',
					} ]
				});				
			}
		});
	</script>
<body>
