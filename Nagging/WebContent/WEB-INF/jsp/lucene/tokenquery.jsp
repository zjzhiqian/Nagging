<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
		<div style="padding-top: 10px">
		    <label for="form-title" style="margin-left: 10px">内容:</label>
		    <input id='content' type="text" name='content'  class="easyui-textbox"   width="150px">
			   <shiro:hasPermission name="lucene:tianyagrab">			
				<a id="query" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="height: 25px">查看分词</a>
			   </shiro:hasPermission>
		<div data-options="region:'north'" style="height:30px">
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div style="margin-left: 50px;margin-top: 50px"><span id="tokens"></span> </div>
		</div>
	</div>
	

</body>

<script type="text/javascript">
	
	$(function(){
		$("#query").click(function(){
			var val=$("#content").val();
			loading("解析中")
			$.ajax({
				url:"${ctx}/lucene/tokenquery",
				data:{content:val},
				success:function(r){
					loaded()
					if(r){
						$("#tokens").html(r)
					}
				}
			})
		})
	});	

	
	
	
	

</script>
</html>