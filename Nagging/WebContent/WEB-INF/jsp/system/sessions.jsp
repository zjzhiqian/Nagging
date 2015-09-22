<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<body>
	<table id="datagrid"></table>

<script type="text/javascript">

	
	
	
	

	function kick(sessionId){
		loading();
		$.ajax({
		  	url:"${ctx}/system/sessions/"+sessionId,
		  	success:function(r){
		  		loaded();
		  		if(r&&r.flag){
		  			$.messager.show({
						msg:r.msg+",请刷新页面",
						title:'提示'
					});
		  		}else{
		  			$.messager.alert("提醒",r.msg,"error");
		  		}
		  	}
	   })
	}
		


	$(function(){
		var editRow=undefined;
		
		var dg=$('#datagrid').datagrid({
			fit:true,
		    url:'${ctx}/system/sessions',
		    columns:[[
		        {field:'id',title:'编号',width:100,checkbox:true},
		        {field:'username',title:'登录名',width:100,align:'right',width:60,
		        	editor:{
		        		type:'textbox',
		        		options:{
		        			required:true,
		        			validType:{
		        				length:[3,10]
		        			}
		        		}
		        	}
		        },
		        {field:'nick',title:'昵称',width:100,align:'right',width:60,
		        	editor:{
		        		type:'textbox',
		        		options:{
		        			required:true,
		        			validType:{
		        				length:[3,10]
		        			}
		        		}
		        	}
		        },
		        {field:'phone',title:'手机号码',width:100,align:'right',width:80,
		        	
		        },
		        
		        {field:'sessionId',title:'session编号',width:100,align:'right',width:30,
		        	 formatter:function(value,row,index){
						  return $.formatString( "<a href='#' onclick=kick('{0}') >踢出</a>",value);
		        	 }
		        }
		    ]],
		    pagination:true,
		    pageSize:50,
		    pageList:[10,20,30,40,50],  
		    fitColumns:true,
		    nowrap:false,
		    border:false,
		  });
		    
	 });
		
		
	
</script>
<body>