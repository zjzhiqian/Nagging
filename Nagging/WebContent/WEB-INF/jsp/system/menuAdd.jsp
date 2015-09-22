
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
		<input type="hidden" value="${pid}">
		<div style="margin-top: 10px">
			<label>父节点:</label>
			<input class="easyui-textbox" type="text" value="${nodeName }" data-options="readonly:true" />
		</div>
		<div style="margin-top: 10px">
			<label>&nbsp;名称:</label> 
			<input class="easyui-textbox" type="text" name="operatename" required="required"/>
		</div>
		<div style="margin-top: 10px">
			<label>&nbsp;描述:</label> 
			<input class="easyui-textbox" type="text" name="description" required="required"/>
		</div>
		<div style="margin-top: 10px">
			<label>&nbsp;图标:</label> 
			<input id="pic" class="easyui-textbox" type="text" name="auth" required="required"/>
			<a href="javaScript:void(0);" id="picture-select">选择</a>
			<div id="menu-image" class="menuImage">
    			<ul id="menu-image-ul"></ul>
			</div>
		</div>
		<div style="margin-top: 10px">
			<label>&nbsp;排序:</label> 
			<input class="easyui-textbox" type="text" name="seq" required="required"/>
		</div>
		<c:if test="${type==1}">
		<div style="margin-top: 10px">
			<label>&nbsp;链接</label> 
			<input class="easyui-textbox" type="text" name="url" required="required"/>
		</div>
		</c:if>
		
	</form>
	
	
	<script type="text/javascript">
			$(function(){
				$("#picture-select").click(function(){
					var  max= $("#pic").offset();
					var min = $("#menuAdd-form").offset();
					console.log(max)
					console.log(min)
					var left = max.left-min.left+5;
					var top = max.top-min.top+50;
					$("#menu-image").slideDown("fast");
					/* $.ajax({   
			            url :'${ctx}/system/menuImageList',
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	$("#menu-image-ul").html("");
			            	for(var i=0;i<json.length;i++){
			            		var html = '<li><a href="javaScript:void(0);" image="'+json[i]+'"><img src="image/menu/'+json[i]+'"></a></li>';
			            		$("#menu-image-ul").append(html);
			            	}
			            }
			        }); */
			        var html = '<li><a href="javaScript:void(0);" image="59"><img src="js/easyui/themes/icons/help.png"></a></li>';
			        for(var i=0;i<50;i++){
			        	$("#menu-image-ul").append(html);
			        }
			        
					$("#menu-image").show();
					
					/* $("body").bind("mousedown", function(event){
						//if (!(event.target.id == "accesscontrol-menu-operateimage" || event.target.id == "accesscontrol-menu-image" || $(event.target).parents("#accesscontrol-menu-image").length>0)) {
							$("#menu-image").hide();
						//}
					}); */
					/* $("#menu-image-ul li img").mouseover(function() {
							$("#menu-image-ul li img").addClass("menuImageOn");
					});
					$("#menu-image-ul li img").mouseover(function() {
							$("#menu-image-ul li img").removeClass("menuImageOn");
					}); */
					$("#menu-image-ul li a").click(function() {
						var image = $(this).attr("image");
						console.log(image)	
						$("#pic").textbox("setValue",image);
						$("#menu-image").hide();
					});
				});
				
			})
	
	</script>
	
</body>

