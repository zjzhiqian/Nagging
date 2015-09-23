
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<body>
	<style type="text/css">
			.menuImage{z-index: 9999; position: absolute;display:none;width:300px;height: 150px;border: 1px solid #AED0EA;background: white;overflow: auto;}
			.menuImage ul{margin-left: -20px;}
			.menuImage li{list-style: none;float: left;width: 18px;height:18px;}
			.menuImageOn{border:1px solid #ddd;}
	</style>


	<form id="menuAdd-form" align="center">
		<div style="margin-top: 10px">
			<label>&nbsp;名称:</label> 
			<input class="easyui-textbox" type="text" name="operatename" required="required" value="${permission.operatename }"/>
		</div>
		<div style="margin-top: 10px">
			<label>&nbsp;描述:</label> 
			<input class="easyui-textbox" type="text" name="description" required="required" value="${permission.description }"/>
		</div>
		<c:if test="${permission.type!=3}">
			<div style="margin-top: 10px">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;图标:</label> 
				<input width="150px" id="pic" class="easyui-textbox" type="text" name="auth" required="required" readonly="readonly" value="${permission.auth }"/>
				<a href="javaScript:void(0);" id="picture-select">选择</a>
				<div id="menu-image" class="menuImage">
	    			<ul id="menu-image-ul"></ul>
				</div>
			</div>
		</c:if>
		<c:if test="${permission.type==3}">
			<div style="margin-top: 10px">
				<label>&nbsp;权限:</label> 
				<input width="150px" id="pic" class="easyui-textbox" type="text" name="auth" required="required" value="${permission.auth }"/>
			</div>
		</c:if>
		<div style="margin-top: 10px">
			<label>&nbsp;排序:</label> 
			<input class="easyui-textbox" type="text" name="seq" required="required" data-options="validType:['integer']" value="${permission.seq }"/>
		</div>
		<c:if test="${permission.type==2}">
		<div style="margin-top: 10px">
			<label>&nbsp;链接</label> 
			<input class="easyui-textbox" type="text" name="url" required="required" value="${permission.url }"/>
		</div>
		</c:if>
		
	</form>
	
	
	<script type="text/javascript">
			$(function(){
				<c:if test="${permission.type!=3}">
				$("#picture-select").click(function(){
					$("#menu-image").slideDown("fast");
					$.ajax({   
			            url :'${ctx}/system/menuImageList',
			            type:'post',
			            dataType:'json',
			            async:false,
			            success:function(rs){
			            	$("#menu-image-ul").html("");
			            	for(var i=0;i<rs.length;i++){
			            		var icon=rs[i].icon;
			            		var url="${ctx}/js/easyui/themes/"+rs[i].url;
			            		var html=$.formatString("<li><a href='javaScript:void(0)';><img value='{0}' src='{1}'></a></li>",icon,url);
			            		$("#menu-image-ul").append(html); 
			            	}
			            }
			        });
					$("#menu-image").show();
					
					$("body").bind("mousedown", function(event){
						if (!(event.target.id == "menu-operateimage" || event.target.id == "menu-image" || $(event.target).parents("#menu-image").length>0)) {
							$("#menu-image").hide();
						}
					});
					$("#menu-image-ul li img").mouseover(function() {
							$(this).addClass("menuImageOn");
					});
					$("#menu-image-ul li img").mouseout(function() {
							$(this).removeClass("menuImageOn");
					});
					
					$("#menu-image-ul li a img").click(function(){
						var image = $(this).attr("value");
						$("#pic").textbox("setValue",image);
						$("#menu-image").hide();
					});
				});
				</c:if>
				
				$("#btn-menuEdit").click(function(){
					if($("#menuAdd-form").form("validate")){
						loading();
						$.ajax({
							url :'${ctx}/system/editmenu/${permission.id}',
			            	type:'post',
			            	data:$("#menuAdd-form").serialize(),
			            	success:function(r){
			            		loaded();
			            		if(r&&r.flag){
			            			showmsg(r.msg)
			            			parent.$("#menu-tree").tree('reload');
			            			parent.$.modalDialog.handler.dialog('close');
			            		}else{
			            			alerterror(r.msg);
			            		}
			            	}
						})
					
					};
				
				})
				
				
				
				
			})
	
	</script>
	
</body>

