<!DOCTYPE html PUBLIC "-//W3C//DTD html 4.01 Transitional//EN">
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
<title>平台</title>
<script type="text/javascript">
  	
	//修改密码方法
	function changePsw(){
	
		parent.$.modalDialog({
			href:"${ctx}/system/changePsw",
			title:'修改密码',
			cache: false,
			modal: true,
			width: 400,
    		height: 200,
    		buttons : [ {
				id:'btn-changepsw',
				text : '确认修改',
				width : 100,
				iconCls:'icon-edit',
			}]
		});
	}
	

	//退出系统方法
	function logout() {
		$.messager.confirm('消息','您确定要退出本系统吗',
				function(r){
					if(r){
						top.location.href="${ctx}/logout";
					}	
				}
		)
		return false;
	}
	
	
	var index_tree;
	var index_tabs;
	var index_tabsMenu;
	$(function(){
		
		
		
		
		index_tabs = $('#index_tabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				index_tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			},
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					var href = index_tabs.tabs('getSelected').panel('options').href;
					if (href) {/*说明tab是以href方式引入的目标页面*/
						var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
						index_tabs.tabs('getTab', index).panel('refresh');
					} else {/*说明tab是以content方式引入的目标页面*/
						var panel = index_tabs.tabs('getSelected').panel('panel');
						var frame = panel.find('iframe');
						try {
							if (frame.length > 0) {
								for ( var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
							
						}
					}
				}
			}, {
				iconCls : 'icon-cancel',
				handler : function() {
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					var tab = index_tabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						index_tabs.tabs('close', index);
					} else {
						$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
					}
				}
			} ]
		});
		
		
		index_tabsMenu = $('#index_tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');
				if (type === 'refresh') {
					var href = index_tabs.tabs('getTab',curTabTitle).panel('options').href;
					if (href) {/*说明tab是以href方式引入的目标页面*/
						var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getTab',curTabTitle));
						index_tabs.tabs('getTab', index).panel('refresh');
					} else {/*说明tab是以content方式引入的目标页面*/
						var panel = index_tabs.tabs('getTab',curTabTitle).panel('panel');
						var frame = panel.find('iframe');
						try {
							if (frame.length > 0) {
								for ( var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
							
						}
					}
				}

				if (type === 'close') {
					var t = index_tabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						index_tabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = index_tabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					index_tabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});
		
		
		
		
	});
</script>


</head>

<body>
	<div id="changePswDialog" ></div>
	
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
    	
    	
    	<div style='background: url("images/layout-browser-hd-bg.gif") ; height: 23px; color: rgb(255, 255, 255); line-height: 20px; overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;' region="north">
		<img  src="images/block.gif" width="20" height="23" style="float: left"> 
		<span style="padding-left: 10px; float: none; " >欢迎进入系统</span>
		<span style="padding-right: 50px; float: right;">欢迎当前用户：${user.nick }
			<a href="#" onclick="changePsw()" style="padding-left: 20px"><font color="white">修改密码</font></a> &nbsp;&nbsp;
			<a  href="#" onclick="logout()"><font color="white">退出系统</font></a>
		</span>
		</div>
    	<div data-options="region:'west',title:'菜单',split:true"  href="${ctx }/system/navigation" style="width:200px;">   
    	</div>
    		
    	<div data-options="region:'center'" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<iframe src="${ctx }/system/menus" frameborder="0"
						style="border: 0; width: 100%; height: 98%;"></iframe>
				</div>
			</div>
		</div>
		
	</div>
	 

	<div id="index_tabsMenu" style="width: 120px; display: none;">
		<div title="refresh" data-options="iconCls:'icon-reload'">刷新</div>
		<div class="menu-sep"></div>
		<div title="close" data-options="iconCls:'icon-cancel'">关闭</div>
		<div title="closeOther" data-options="iconCls:'icon-cancel'">关闭其他</div>
		<div title="closeAll" data-options="iconCls:'icon-cancel'">关闭所有</div>
	</div>
</body>
</html>
