<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
var index_tree;
$(function(){
	index_tree=$("#index_tree").tree({
		url:'${ctx}/system/showMenus/13',
	    lines:true,
	    onClick : function(node) {
	    	//如果是2级菜单
			if (node.attributes&&node.attributes.url&&node.attributes.url.length>0) {
				if (node.attributes.url.indexOf('/') == 0) {
					url = '${ctx}' + node.attributes.url;
				} else {
					url = node.attributes.url;
				}
				addTab({
					url : url,
					title : node.text,
					iconCls : node.iconCls
				});
			}else{
				//如果是1级菜单
				if(node.state=='closed'){
					$(this).tree('expand',node.target);
				}else{
					$(this).tree('collapse',node.target);
				}
			}
		},
		onBeforeLoad : function(node, param) {
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
		},
		onLoadSuccess : function(node, data) {
			parent.$.messager.progress('close');
		}
	})
	
	
	function addTab(params) {
		var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
		var t = $('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true
		};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
	}

	
	
	
})
</script>

	
<div class="easyui-accordion" data-options="fit:true,border:false" style="margin-bottom: 30px">
	<div title="系统菜单" style="padding: 5px;" data-options="border:false,isonCls:'anchor'">
		<ul id="index_tree"></ul>
	</div>
	<div title="lucence模块" data-options="border:false">
		
	</div>
	<div title="其他示例" data-options="border:false" ">
		
	</div>
</div>


