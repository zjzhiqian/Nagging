<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<body>

	<div class="easyui-panel" data-options="fit:true" style="padding:2px;">

			
		<div class="easyui-layout" data-options="fit:true">  
            <div title="角色列表" data-options="region:'west',split:false,border:false" style="width:350px;">
		     	<table id="datagrid"></table>
		    </div>
		    <div title="角色详情" data-options="region:'center',split:false,border:false">
		    	<div id="roleInfo" class="easyui-panel"/>
			</div>
	   </div>
			
			
   </div>

<script type="text/javascript">
	$(function(){
		var editRow=undefined;
		
		var dg=$('#datagrid').datagrid({
			fit:true,
		    url:'${ctx}/system/roles',
		    columns:[[
		        {field:'authorityname',title:'角色名称',width:100,
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
		        {field:'description',title:'角色描述',width:100,align:'right',
		        	editor:{
		        		type:'textbox',
		        		options:{
		        			required:true,
		        			validType:{
		        				length:[3,20]
		        			}
		        		}
		        	}
		        },
		        {field:'state',title:'状态',width:100,
		        	formatter:function(value,row,index){
		        		if(value=='1'){
		        			return '启用';
		        		}else if(value=='0'){
		        			return '禁用';
		        		}else {
		        			return '';
		        		}
		        	}
		        },
		        {field:'addtime',title:'添加时间',width:100,hidden:true},
		        {field:'adduserid',title:'添加人用户编号',width:100,hidden:true},
		        {field:'modifytime',title:'修改时间',width:100,hidden:true},
		        {field:'modifyuserid',title:'修改用户编号',width:100,hidden:true},
		    ]],
		    rownumbers:true,
		    singleSelect:true,
		    onClickRow:function(rowIndex, rowData){
			    	var url = "${ctx}/system/roleDetail/"+rowData.id;
					$("#roleInfo").panel({  
					    href:url,
					    cache:false,
					    maximized:true
					});
			},
		    toolbar:[
					<shiro:hasPermission name="role:add">
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
		    		 <shiro:hasPermission name="role:edit">	
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
							  /* dg.datagrid('rejectChanges');
							  editRow==undefined; */
							  if(editRow==undefined){
								   var index=dg.datagrid('getRowIndex',rows[0])
						    	   dg.datagrid('beginEdit',index);
						    	   editRow=index;
						    	   dg.datagrid('unselectAll');
					    	   }
						  }else{
							  $.messager.alert('提醒','请选择一条数据','info');
						  }
						  
						  
				      }
					},'-',
					</shiro:hasPermission>	
					<shiro:hasPermission name="role:delete">
					{
					  iconCls: 'icon-remove',
				      text:'删除',
				      handler: function(){
						  if(editRow!=undefined){
				    		  $.messager.alert("提醒","请先完成当前操作","warning");
				    		  return false;
				    	  }
				    	  var row=dg.datagrid('getSelected');
				    	  if(row){
				    		  $.messager.confirm('请确认','确认要删除此角色嘛?',function(r){
				    			  if(r){
				    			  	 loading("删除中");
				    				 $.ajax({
				    					 url:'${ctx}/system/roledelete/'+row.id,
				    					 type:'post',
				    					 dataType:'json',
				    					 success:function(r){
				    					 	 loaded();
				    						 if(r&&r.flag){
				    							 showmsg(r.msg);
				    							 dg.datagrid('load');
				    							 dg.datagrid('unselectAll');
				    							 $("#roleInfo").panel('clear');
				    						 }else{
				    							 alerterror(r.msg)
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
					url='${ctx}/system/roleadd';
				}
				if(updated.length>0){
					url='${ctx}/system/roleedit';
				}
				if(url==""){
					editRow=undefined;
					return false;
				}
				loading();
				$.ajax({
					url:url,
					type:'post',
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