<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%><html>
<head>
<title>天涯帖子页面</title>
</head>


<body>
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
		<div data-options="region:'north'" style="height:60px;overflow: hidden">
		
			<form class="form-inline" style="margin-top: 2px" id="form-commonQuery">
				  <div>
				    <label for="form-title" style="margin-left: 10px">标题:</label>
				    <input type="text" name='title' id="form-title" class="easyui-textbox"  width="150px">
				    <label style="margin-left: 60px">内容:</label>
				    <input type="text" name='content' class="easyui-textbox"  width="150px" >
				    
				    <label style="margin-left: 60px">发帖时间:</label>
				    <input type="text" name='postTime1' class="easyui-datetimebox" >
				        至<input type="text" name='postTime2' class="easyui-datetimebox">
				  </div>
					
				  <div>
				  	<label style="margin-left: 10px">排序:</label>
				  	<select id="cc" class="easyui-combobox" name="sort" style="width:180px;">
				  		<option value=""></option>
					    <option value="1">发帖时间</option>
					    <option value="2">点击数</option>
					    <option value="3">回复数</option>
					</select>
				    <shiro:hasPermission name="lucene:tianyapostquery">	
					<a id="reset" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="height: 25px;margin-left: 500px">重置</a>		
					<a id="commonquery" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height: 25px">模糊查询</a>
					<a id="indexquery" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height: 25px">lucene检索(单目录)</a>
					<a id="indexqueryMulti" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="height: 25px">lucene检索(多目录)</a>
				    </shiro:hasPermission>
				  </div>
			</form>
			
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<table id="datagrid"></table>
		</div>	
	</div>
	<script>
		$(function(){
			dg=$('#datagrid').datagrid({
				fit:true,
			    url:'${ctx}/lucene/tianyaquery',
			    columns:[[
// 			        {field:'id',title:'编号',width:100,checkbox:true},
					{field:'id',title:'编号',width:100},
			        {field:'title',title:'标题',width:300},
			        {field:'content',title:'内容',width:400},
			        {field:'adduserId',title:'发帖人编号',width:60,align:'right'},
			        {field:'adduserName',title:'发帖人',width:100},
			        {field:'addTime',title:'发帖时间',width:100},
			        {field:'lastReplyTime',title:'最后回复时间',width:100},
			        {field:'click',title:'点击数',width:60,align:'right'},
			        {field:'reply',title:'回复数',width:60,align:'right'},
			        {field:'isBest',title:'是否加精',width:60,align:'right',
			        	formatter:function(value,rowData,rowIndex){
		        			if(value=="1"){
		        				return "是";
		        			}else if (value=="0"){
		        				return "否";
		        			}
		        		}
			        },
			        
			    ]],
			    pagination:true,
			    pageSize:50,
			    pageList:[10,20,30,40,50],  
			    fitColumns:true,
			    border:false,
			    toolbar:[
						
				],
				onLoadSuccess:function(data){
					if(data.o&&data.o.flag){
						showmsg($.formatString("加载数据一共用了{0}秒",Number(data.o.msg)/1000))
					}else{
						alerterror(data.o.msg)
					}
				}
			    
			});
			
		});
		
		$("#commonquery").click(function(){
			queryParams=$.serializeObject($("#form-commonQuery"))
			dg.datagrid({
				url:'${ctx}/lucene/tianyaquery',
				queryParams:queryParams
			})
		})
		
		$("#reset").click(function(){
			dg.datagrid({
				url:'${ctx}/lucene/tianyaquery'
			})
			$("#form-commonQuery input").val("")
			dg.datagrid('load',{});
		})
		
		//查询,单目录索引
		$("#indexquery").click(function(){
			var url="${ctx}/lucene/tianyaIndexQuery/1";
			queryParams=$.serializeObject($("#form-commonQuery"))
			dg.datagrid({
				url:url,
				queryParams:queryParams
			});
		})	
		
		//查询,多目录索引
		$("#indexqueryMulti").click(function(){
			var url="${ctx}/lucene/tianyaIndexQuery/2";
			queryParams=$.serializeObject($("#form-commonQuery"))
			dg.datagrid({
				url:url,
				queryParams:queryParams
			});
		})	
		
	
	</script>	
</body>


</html>