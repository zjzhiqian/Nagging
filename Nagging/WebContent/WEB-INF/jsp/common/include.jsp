<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>

<script type="text/javascript" src="${ctx}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrapt/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/extBrowser.js"></script>
<script type="text/javascript" src="${ctx}/js/extEasyUI.js"></script>
<script type="text/javascript" src="${ctx}/js/extJquery.js"></script>
<script type="text/javascript" src="${ctx}/js/extJs.js"></script>
<body>
	<%
		String easyuiTheme="default";
	 %>
</body>

<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrapt/bootstrap.min.css">
<link rel="stylesheet" type="text/css"  href="${ctx}/js/easyui/themes/<%=easyuiTheme %>/easyui.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css"  />

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
  		//设置ajax请求通用处理
  		jQuery.ajaxSetup({
  			type : 'POST',
  			statusCode : {
  				/* 900: function(){
  					alert("用户登录数量超过系统限制！");
    				top.location.href="login";
  				}, */
  				401: function(){
  					$.messager.alert("错误","未登录！","error");
  					top.location.href="${ctx}/login";
  				},
  				404: function(){
  					$.messager.alert("错误","页面找不到！","error");
  				},
  				700 : function(){
  					alert("账号异地登陆")
  					top.location.href="${ctx}/login";
  				}
  			},
  			beforeSend: function (xhr) {
  				
            },
  			error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
	  			try {
	  					loaded();
						alert("进了ERROR方法")
						//parent.$.messager.alert('错误', XMLHttpRequest.responseText);
				} catch (e) {
						alert(XMLHttpRequest.responseText);
				}
  		    }
  		});
	
		
  		
  		
  		
  		
	
		function loading(msg){
			var text="提交中....";
			if(msg){
				text=msg;
			}
			parent.$.messager.progress({
					title : '提示',
					text : text
			});
		}
		function loaded(){
			parent.$.messager.progress('close');
		}
		
		function alertinfo(msg){
			$.messager.alert('提示',msg,'info')
		}
		
		function alerterror(msg){
			$.messager.alert('错误',msg,'error');
		}
		function alertwarning(msg){
			$.messager.alert('警告',msg,'warning');
		}
		
		function showmsg(msg){
			$.messager.show({
				msg:msg,
				title:'提示'
			});
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
</script>		




