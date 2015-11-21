<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>数据抓取页面</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
	
		<div data-options="region:'north'" style="height:30px">
		   <shiro:hasPermission name="lucene:tianyagrab">			
			<a id="data-grab" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="height: 25px">简单抓取</a>
		   </shiro:hasPermission>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div style="margin-left: 50px;margin-top: 50px">数据已抓取数量 : <span id="data-num">0</span> </div>
		</div>
	</div>
	

</body>

<script type="text/javascript">
	
	
	
	
	
	
	

</script>
</html>