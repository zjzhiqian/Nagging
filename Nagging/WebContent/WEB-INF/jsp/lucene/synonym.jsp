<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>同义词管理页面</title>
</head>
<body>
		<div id="dl"></div>
		
		
		
</body>

<script type="text/javascript">
	$(function(){
		
		$('#dl').datalist({
		    url: '${ctx}/lucene/synonym/${type}',
		    checkbox: true,
		    lines: true,
		    pagination:true,
		    width:500,
		    height:750,
		    pageSize:50
		});	
		
	
	})

  
	
	

</script>
</html>