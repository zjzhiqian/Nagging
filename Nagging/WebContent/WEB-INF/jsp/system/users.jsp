<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<body>
	<table id="datagrid"></table>


	<script type="text/javascript">
		var dg;
	
		function updRole(id){
			dg.datagrid('unselectAll');
			dg.datagrid('uncheckAll');
			parent.$.modalDialog({
				title : '用户授权',
				width : 500,
				height : 300,
				href : '${ctx}/system/authorization/' + id,
				buttons : [ {
					id:'btn-auth',
					text : '授权',
					width : 100
				}
			  ]
			});
		}



	$(function(){
		var editRow=undefined;
		
		 dg=$('#datagrid').datagrid({
			fit:true,
		    url:'${ctx}/system/users',
		    columns:[[
		        {field:'id',title:'编号',width:100,checkbox:true},
		        {field:'username',title:'登录名',width:100,align:'right',
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
		        {field:'name',title:'昵称',width:100,align:'right',
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
		        {field:'phone',title:'手机号码',width:100,align:'right',
		        	editor:{
		        		type:'textbox',
		        		options:{
		        			required:true
		        		}
		        	}	
		        },
		        {field:'state',title:'状态',width:100,
		        	formatter:function(value,row,index){
		        		if(value=='1'){
		        			return '启用';
		        		}else if(value=='2'){
		        			return '禁用';
		        		}else {
		        			return '';
		        		}
		        	}
		        },
		        {field:'addtime',title:'添加时间',width:160},
		        {field:'adduserid',title:'添加用户编号',align:'right',width:100,hidden:true},
		        {field:'modifytime',title:'修改时间',width:160},
		        {field:'modifyuserid',title:'修改用户编号',align:'right',width:100,hidden:true},
		        {field:'lastip',title:'上次登录ip',align:'right',width:100},
		        {field:'loginTime',title:'上次登录时间',width:160},
		        {field:'loginCount',title:'总登录次数',width:100,align:'right'},
		        {field:'role',title:'修改角色',width:100,align:'right',
		        	formatter:function(value,rowData,rowIndex){
		        		return $.formatString('<a href="#" onclick="updRole(\'{0}\');">授权</a>', rowData.id);
		        	}
		        }
		        
		    ]],
		    pagination:true,
		    pageSize:50,
		    pageList:[10,20,30,40,50],  
		    fitColumns:true,
		    border:false,
		    onDblClickRow:function(rowIndex,rowData){
		      <shiro:hasPermission name="user:edit">
		    	if(editRow!=undefined){
		    		  $.messager.alert("提醒","请先完成当前操作","warning");
		    		  return false;
		    	 }
		    	dg.datagrid('unselectAll');
		    	dg.datagrid('beginEdit',rowIndex);
		    	editRow=rowIndex;
		      </shiro:hasPermission>
		    },
		    toolbar:[
					<shiro:hasPermission name="user:add">
		             {
		               iconCls: 'icon-add',
					   text:'新增',
				       handler: function(){
				    	   if(editRow!=undefined){
				    		  $.messager.alert("提醒","请先完成当前操作","warning");
				    		  return false;
				    	   }
				    	   if(editRow==undefined){
				    		   dg.datagrid('insertRow',{
									  index:0,
									  row:{
										  
									  }
					    	   });
					    	   dg.datagrid('beginEdit',0);
					    	   editRow=0;
				    	   }
				       }
		    		 },'-',
		    		 </shiro:hasPermission>
		    		 <shiro:hasPermission name="user:edit">	
					{	
					  iconCls: 'icon-edit',
					  text:'修改',
					  handler: function(){
						  if(editRow!=undefined){
				    		  $.messager.alert("提醒","请先完成当前操作","warning");
				    		  return false;
				    	  }
						  var rows=dg.datagrid('getSelections');
						  if(rows&&rows.length==1){
							  
							  if(editRow==undefined){
								   var index=dg.datagrid('getRowIndex',rows[0])
						    	   dg.datagrid('beginEdit',index);
								   dg.datagrid('getEditor', {index:'1',field: 'username' }).disable();
						    	   editRow=index;
						    	   dg.datagrid('unselectAll');
					    	   }
						  }else{
							  $.messager.alert('提醒','请选择一条数据','info');
						  }
						  
						  
				      }
					},'-',
					</shiro:hasPermission>	
					<shiro:hasPermission name="user:delete">
					{
					  iconCls: 'icon-remove',
				      text:'删除',
				      handler: function(){
						  if(editRow!=undefined){
				    		  $.messager.alert("提醒","请先完成当前操作","warning");
				    		  return false;
				    	  }
				    	  var rows=dg.datagrid('getSelections');
				    	  if(rows.length>0){
				    		  $.messager.confirm('请确认','确认要删除当前所有选择数据吗?',function(r){
				    			  if(r){
				    				  var ids=[];
				    				  for(var i=0;i<rows.length;i++){
				    					  ids.push(rows[i].id);
				    				  }
				    				 $.ajax({
				    					 url:'${ctx}/system/userdelete',
				    					 type:'post',
				    					 dataType:'json',
				    					 data:{"ids":ids.join(',')},
				    					 success:function(r){
				    						 if(r&&r.msg){
				    							 $.messager.show({
				    									msg:r.msg,
				    									title:'提示'
				    							});
				    							 dg.datagrid('load');
				    							 dg.datagrid('unselectAll');
				    						 }else{
				    							 $.messager.alert('错误',r.msg,'error');
				    							 dg.datagrid('load');
				    							 dg.datagrid('unselectAll');
				    						 }
				    					 }
				    					 
				    				 })
				    			  }
				    		  })
				    	  }else{
				    		  $.messager.alert('提示','请选择要删除的记录','error')
				    	  }
				      }
				    },'-',
				    </shiro:hasPermission>
				    {	
					 iconCls: 'icon-save',
				     text:'保存',
				     handler: function(){
				    	if(editRow!=undefined){
				    		dg.datagrid('endEdit',editRow);
				    	}
					 }
					},'-',
					{	
						 iconCls: 'icon-redo',
					     text:'取消编辑',
					     handler: function(){
					    	 editRow=undefined;
					    	 dg.datagrid('rejectChanges');
					    	 dg.datagrid('unselectAll');
						 }
					},'-',
					
					],
			onAfterEdit:function(index,rowData,changes){
				var inserted=dg.datagrid('getChanges','inserted');
				var updated=dg.datagrid('getChanges','updated');
				var url=""; var json="";
				if(inserted.length>0){
					url='${ctx}/system/useradd';
				}
				if(updated.length>0){
					url='${ctx}/system/useredit';
				}
				if(url==""){
					editRow=undefined;
					return false;
				}
				
				loading();
				$.ajax({
					url:url,
					data:rowData,
					success:function(r){
						loaded();
						if(r&&r.flag){
							showmsg(r.msg)
							dg.datagrid('acceptChanges');
							dg.datagrid('load');
						}else{
							dg.datagrid('rejectChanges');
							alerterror(r.msg)
						}
						editRow=undefined;
						dg.datagrid('unselectAll');
					}
				})
			   
		  }
		    
		});
		
		
		
	})

</script>
<body>