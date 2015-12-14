<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
	$(function() {
		
		$("#title").textbox({
			width:300,
			height:20,
			required: true,
			validType:{
				length:[5,50]
			}
		})
		
		$("#content").textbox({
			width:300,
			height:50,
			required: true,
			validType:{
				length:[5,50]
			},
			multiline:true
		})
		
		$("#save").click(function(){
			if($("form").form('validate')){
				$.ajax({
					url:'${ctx}/lucene/addTianYaPost',
					data:$("form").serialize(),
					success:function(data){
						if(data&&data.flag){
							showmsg(data.msg)
							  $("#title").textbox("setValue","")
							  $("#content").textbox("setValue","")
						}else{
							alerterror(data.msg)
						}
					}
				})
			}
		
		
		})
		
		
	});
	
	
	
	
</script>

</head>
<body>
	
	
	<form style="margin-top: 60px;">
		<label style="margin-left: 44px">title:</label>
		<input type="text" id="title" name="title"/> 
		<br/>
		<label style="margin-left: 20px;margin-top: 20px;">content:</label>
		<input type="text" id="content" name="content"/> 
		<br/>
		<a style="margin-left: 120px;margin-top: 30px;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id='save'>保存</a>
	</form>
</body>
</html>