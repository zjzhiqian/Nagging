<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/js/excel.js"></script>
<html>
<head>
<title>ExcelDemo</title>
</head>


<body>
	<div class="easyui-layout" data-options="fit:true" style="margin-top: 0px">
		<div data-options="region:'north'" style="height:30px;overflow: hidden">
			<form class="form-inline" style="margin-top: 2px" id="form-commonQuery">
				  <div>
				    <a id="import" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">导入</a>
				    <a id="export" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">导出</a>
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
			    onDblClickRow:function(rowIndex,rowData){
			    
			    },
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
		
		$("#export").click(function(){
			$.exportExcel({
				url:"${ctx}/store/extractExcel",
				title:"天涯帖子",
				tb:"tianya_post"
			})
			$(this).linkbutton("disable");
		})
		
		
		$("#import").click(function(){
			$.importExcel({
				url:"${ctx}/store/importExcel",
				clazz:"tianYaPostServiceImpl",
				method:"importExcel",
				module:"module",
				tn:"tn",
				pojo:"TianYaPost",
				datagrid:dg
			})
		})
		
	</script>	
</body>


</html>