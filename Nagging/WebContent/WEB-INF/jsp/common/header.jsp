<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="header">


	<div style='background: url("images/layout-browser-hd-bg.gif") repeat-x center 50% rgb(127, 153, 190); height: 30px; color: rgb(255, 255, 255); line-height: 20px; overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;'
		border="false" split="true" region="north">
		<span style="padding-right: 20px; float: right;" class="head">欢迎当前用户：${activeUser.username}&nbsp;&nbsp;
			<a href=javascript:showhelp()>使用帮助</a>&nbsp;&nbsp;
			<a title='修改密码' ref='modifypwd' href="#" rel='${ctx}/user/updatepwd.action' icon='icon-null' id="modifypwd" >修改密码</a> &nbsp;&nbsp;
			<a id="loginOut" href=${ctx}/logout>退出系统</a>
		</span> <span style="padding-left: 10px; font-size: 16px;">
		<img align="absmiddle" src="images/blocks.gif" width="20" height="20"> 欢迎进入系统</span> <span style="padding-left: 15px;" id="News"></span>
	</div>
	
	
</div>

